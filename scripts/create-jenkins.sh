#!/usr/bin/env bash
JENKINS_HOMEDIR="/Users/Shared/Jenkins"
DEFAULTS_PLIST="/Library/Preferences/org.jenkins-ci.plist"

if dscl . -list /Users/jenkins; then
    echo 'jenkins user already exists, attempting to change the shell to /bin/bash'
    # Will fail if UserShell is not /usr/bin/false, but that's ok.
    # Then we will assume an admin has changed it.
    dscl . -change /Users/jenkins UserShell /usr/bin/false /bin/bash
else
    echo 'No jenkins user found, creating jenkins user and group'

# Find free uid under 500
    uid=$(dscl . -list /Users uid | sort -nrk 2 | awk '$2 < 500 {print $2 + 1; exit 0}')
    if [[ ${uid} -eq 500 ]]; then
        echo 'ERROR: All system uids are in use!'
        exit 1
    fi
    echo "Using uid $uid for jenkins"

    gid=$uid
    while dscl -search /Groups gid $gid | grep -q $gid; do
        echo "gid $gid is not free, trying next"
        gid=$(($gid + 1))
    done
    echo "Using gid $gid for jenkins"

    dscl . -create /Groups/jenkins PrimaryGroupID $gid

    dscl . -create /Users/jenkins UserShell /bin/bash
    dscl . -create /Users/jenkins Password '*'
    dscl . -create /Users/jenkins UniqueID $uid
    dscl . -create /Users/jenkins PrimaryGroupID $gid
    dscl . -create /Users/jenkins NFSHomeDirectory "$JENKINS_HOMEDIR"

    dscl . -append /Groups/jenkins GroupMembership jenkins
fi
