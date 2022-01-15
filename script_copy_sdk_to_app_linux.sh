# copy generated .aar file to app module
#
set -e

echo "Copy SDK to sample App"
mkdir -p app/libs
cp heart-rate-chicken-embryos-sdk/build/outputs/aar/heart-rate-chicken-embryos-sdk-debug.aar app/libs
echo "Done"