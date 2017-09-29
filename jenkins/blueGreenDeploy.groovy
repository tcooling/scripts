/*
* @method deploy()
*
* @description This method will deploy an app to CloudFoundry, using the
* supplied app/credentials. After the initial deploy, subsequent deploys
* will use the blue/green deployment technique to reduce downtime.
*
* @param {String} credentialsId - id of Jenkins credentials to login to cloud foundry
* @param {String} organisation - cloud foundry organisation
* @param {String} space - cloud foundry space within the organisation
* @param {String} appName - the application name
* @param {String} pathToArtifact - application to deploy
* @param {String} pathToManifest - manifest to use during deployment
* @param {String} host - cloud foundry host
* @param {String} domain - domain location
*
*/
def deploy(String credentialsId, String organisation, String space, String appName, String pathToArtifact, String pathToManifest, String host, String domain) {
    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: credentialsId, usernameVariable: 'CF_USERNAME', passwordVariable: 'CF_PASSWORD']]) {
        withEnv(["HOST=${host}","ORG=${organisation}", "SPACE=${space}", "APP_NAME=${appName}", "MANIFEST=${pathToManifest}", "APP=${pathToArtifact}", "DOMAIN={domain}"]) {
            sh '''
                cf login -a $HOST -u $CF_USERNAME -p $CF_PASSWORD -o $ORG -s $SPACE
        
                # Use set +e/-e to turn on/off an exit code failing the script
                # This is needed as if no app is present, the command will fail
                set +e
                cf app "${APP_NAME}"
                APP_PRESENT=$?
                set -e
        
                # If the app is already there, do the blue/green deployment
                # Otherwise, do the initial deployment
                if [ "$APP_PRESENT" = "0" ]
                then
                    echo "App present in CF, doing blue/green deploy..."
                    # Used below source for awk script
                    # https://www.fabian-keller.de/blog/blue-green-deployment-with-cloudfoundry
                    INSTANCES=$(cf app "${APP_NAME}"|grep ^instances|awk '{print $2}'|awk -F/ '{print $2}')
                    echo $INSTANCES
                    cf push "${APP_NAME}-new" -f $MANIFEST -p $APP -n "${APP_NAME}-new" -i ${INSTANCES}
                    cf map-route "${APP_NAME}-new" $DOMAIN -n "${APP_NAME}"
                    cf unmap-route "${APP_NAME}" $DOMAIN  -n "${APP_NAME}"
                    cf delete "${APP_NAME}" -f
                    cf rename "${APP_NAME}-new" "${APP_NAME}"
                else
                    echo "No apps in CF, doing initial deploy..."
                    cf push "${APP_NAME}" -f $MANIFEST -p $APP -n "${APP_NAME}"
                fi
        
                cf logout
            '''
        }
    }
}
