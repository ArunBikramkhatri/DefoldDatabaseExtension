# git reset --mixed HEAD~1
# git add .
# git commit -m "bug fix"
git push origin -f
git tag -d v1.0.0.0_1_android  
git tag -a v1.0.0.0_1_android -m "version code 1 version name 1.0.0.0"
git push -f --tags