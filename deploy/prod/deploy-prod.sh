set -e

IMAGE_TAG="$1"

if [ -z "$IMAGE_TAG" ]; then
    echo "❌ Error: Image tag argument is missing!"
    echo "Usage: ./deploy-prod.sh <IMAGE_TAG>"
    exit 1
fi

echo "Target Image Tag: $IMAGE_TAG"


DEPLOY_PATH="/home/kim/apps/savvyers/deploy/prod/server"
BLUE_CONTAINER="savvyers-server-prod-blue"
GREEN_CONTAINER="savvyers-server-prod-green"
BLUE_PORT="5201"
GREEN_PORT="5202"

cd $DEPLOY_PATH

echo "=========================================="
echo "[STEP 1/8] Loading Docker image..."
echo "=========================================="
docker load -i savvyers-server-prod-image.tar
echo "✓ Image loaded successfully"

echo ""
echo "=========================================="
echo "[STEP 2/8] Cleaning up tarball..."
echo "=========================================="
rm -f savvyers-server-prod-image.tar
echo "✓ Tarball removed"

echo ""
echo "=========================================="
echo "[STEP 3/8] Determining deploy target..."
echo "=========================================="

# Default: deploy to Blue
DEPLOY_TARGET="blue"
DEPLOY_CONTAINER="$BLUE_CONTAINER"
DEPLOY_PORT="$BLUE_PORT"

# Check if blue container is running, if so deploy to Green
IS_BLUE=$(docker ps | grep savvyers-server-prod-blue || true)
echo "IS_BLUE: [$IS_BLUE]"

if [ "$IS_BLUE" != "" ]; then
    echo "Blue is running → deploying to Green"
    DEPLOY_TARGET="green"
    DEPLOY_CONTAINER="$GREEN_CONTAINER"
    DEPLOY_PORT="$GREEN_PORT"
else
    echo "Blue is not running → deploying to Blue"
fi

echo "→ Deploy target: $DEPLOY_TARGET (port $DEPLOY_PORT)"
echo "✓ Target determined"

echo ""
echo "=========================================="
echo "[STEP 4/8] Setting up network..."
echo "=========================================="
docker network inspect shared-elastic >/dev/null 2>&1 || docker network create shared-elastic
echo "✓ Network ready"

echo ""
echo "=========================================="
echo "[STEP 5/8] Deploying container..."
echo "=========================================="
echo "Removing old container if exists..."
docker rm -f "$DEPLOY_CONTAINER" || true

echo "Checking env file..."
ls -la $DEPLOY_PATH/env/.env.prod || echo "WARNING: env file not found!"

echo "Starting new container..."
docker run -d --name "$DEPLOY_CONTAINER" --restart unless-stopped \
    -p "$DEPLOY_PORT":8080 \
    --network shared-elastic \
    --env-file $DEPLOY_PATH/env/.env.prod \
    "$IMAGE_TAG"

echo "Container started, checking status..."
docker ps | grep "$DEPLOY_CONTAINER" || echo "WARNING: Container not found in docker ps!"
echo "✓ Container deployed"

echo ""
echo "=========================================="
echo "[STEP 6/8] Health check..."
echo "=========================================="
MAX_RETRIES=30
RETRY_INTERVAL=2
HEALTH_OK=false
echo "http://localhost:$DEPLOY_PORT/health"

for i in $(seq 1 $MAX_RETRIES); do
    if curl -sf http://localhost:$DEPLOY_PORT/health > /dev/null 2>&1; then
        echo "✓ Health check passed!"
        HEALTH_OK=true
        break
    fi
    echo "Attempt $i/$MAX_RETRIES: waiting..."
    sleep $RETRY_INTERVAL
done

if [ "$HEALTH_OK" = false ]; then
    echo "✗ Health check failed!"
    echo "Container logs:"
    docker logs "$DEPLOY_CONTAINER" --tail 100
    docker rm -f "$DEPLOY_CONTAINER" || true
    exit 1
fi

echo ""
echo "=========================================="
echo "[STEP 7/8] Switching Nginx upstream..."
echo "=========================================="
echo "Copying upstream config..."
sudo cp $DEPLOY_PATH/nginx/upstream-$DEPLOY_TARGET.conf /etc/nginx/includes/savvyers-server-upstream.conf
echo "✓ Config copied"

echo "Testing nginx config..."
sudo nginx -t
echo "✓ Config valid"

echo "Reloading nginx..."
sudo systemctl reload nginx
echo "✓ Nginx reloaded"

sleep 2
if curl -sf https://api.savvyers.net/health > /dev/null 2>&1; then
    echo "✓ Traffic switched successfully!"
else
    echo "⚠ Warning: Nginx health check failed"
fi

echo ""
echo "=========================================="
echo "[STEP 8/8] Cleaning up old container..."
echo "=========================================="
if [ "$DEPLOY_TARGET" = "blue" ]; then
    OLD_CONTAINER="$GREEN_CONTAINER"
else
    OLD_CONTAINER="$BLUE_CONTAINER"
fi

docker rm -f "$OLD_CONTAINER" 2>/dev/null && echo "✓ Old container removed" || echo "✓ No old container to remove"


echo ""
echo "=========================================="
echo "✓ DEPLOYMENT COMPLETED!"
echo "  Active: $DEPLOY_TARGET"
echo "=========================================="
