echo "[Bump Push]"
git pull
echo "resolve git tags"
last_version=`git describe --tags --abbrev=0`
echo "last version: $last_version"
new_version=`bump patch --dry-run --allow-dirty`
echo "new version (bump): $new_version"
echo "update README.md with new versions"
sed -i "s/$last_version/$new_version/g" README.md

git tag $new_version
npx github-release-notes@0.17.1 changelog --token=$(echo $GITHUB_TOKEN) --override
git add *README.md
git add *CHANGELOG.md
git commit -m "chore(version): update version"
git tag $new_version -f

git push && git push --tag

#npx github-release-notes@0.17.1 release --token=$(echo $GREN_GITHUB_TOKEN) -P
