#!/bin/bash
echo --------------------------------------------------------------------------------
echo "- installJenkins.sh start"
echo --------------------------------------------------------------------------------

apt-get -y install gradle
apt-get -y install jenkins
apt-get -y install maven
apath=$(dirname $(ls -als $(ls -als $(which mvn) |awk '{print $NF}')|awk '{print $NF}'))
echo export M2_HOME=${apath%/*} >> /etc/bash.bashrc
echo export MAVEN_HOME=${apath%/*} >> /etc/bash.bashrc
echo export PATH=\$PATH:$apath    >> /etc/bash.bashrc
tail /etc/bash.bashrc
. /etc/bash.bashrc
mvn -v

echo --------------------------------------------------------------------------------
echo "- installJenkins.sh end"
echo --------------------------------------------------------------------------------
