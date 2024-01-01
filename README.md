# Capstone Project

### Architecture
This is a springboot graphql project. The core of this project is to utilize a third party service called Zipcodebase.com that provides location and zip code information.
This app calls those third party API and persist data to internal DynamoDB table to reduce the transaction cost as Zipcodebase charges by transaction.
If the user makes a graphql calls to this app, it then looks up the internal database first for the relevant information and only if not found, it makes calls to third party API to ge the data and persist in internal DB.

### Sample Graphql query:
http://localhost:8080/graphql

**Request body:**

```json
query LocationByZipCode {
    locationByZipCode(zipCode: "22193") {
        id
        postalCode
        countryCode
        latitude
        longitude
        city
        state
        cityEnglish
        stateEnglish
        province
        provinceCode
        allPostalCodes {
            id
            city
            state
            country
            allZipCodes
        }
    }
}

```
**Authentication**
App is built using JWT based oAuth2 using Auth0. An Authentication token is required.

### CICD
Continuous Integration and Continue Deployment is done using Github Actions. See the [repository](https://github.com/safrrhmn/cloud-nd-capstone/blob/3baf865ab643a9583b4e575b04c586d65070212d/.github/workflows/docker-publish.yml) for details.

Deployment is done in EKS cluster using the dockerFile attached to the project. See [dockerFile](https://github.com/safrrhmn/cloud-nd-capstone/blob/4169e042e260b62aeff10cf93035a1ffb7b931b8/Dockerfile) and [deployment.yaml](https://github.com/safrrhmn/cloud-nd-capstone/blob/4ffc50a4bef197ee81b5b2937a3bf0214d46694a/deployment.yaml) for more info.