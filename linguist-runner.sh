#!/usr/bin/bash

for commit in $(git --no-pager log --reverse --after="2019-11-01T00:00:00-03:00" --pretty=format:%H)
do
  echo ${commit}
  git checkout ${commit}

  #write linguist data to a file
  echo "" >> ~/repo-linguist-report.txt
  echo "commit: $commit" >> ~/repo-linguist-report.txt
  github-linguist >> ~/repo-linguist-report.txt

  #write commit dates to another file, because we're lazy
  echo "commit: $commit" >> ~/repo-commit-dates.txt
  git show -s --format=%ci ${commit} >> ~/repo-commit-dates.txt
done
