echo "[Bump Push]"
git pull
echo "resolve git tags"
last_version=`git describe --tags --abbrev=0`
echo "last version: $last_version"
new_version=`bump patch --dry-run --allow-dirty`
echo "new version (bump): $new_version"
git tag $new_version
echo "update README.md with new versions"
sed -i "s/$last_version/$new_version/g" README.md

git add *README.md
git commit -m "chore(version): update version"

git push && git push --tag
