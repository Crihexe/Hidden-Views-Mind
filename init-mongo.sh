#!/bin/bash
password="$(</run/secrets/secrets.mongo.password.default)"

mongosh <<EOF
use ${MONGO_INITDB_DATABASE}
db.createCollection("posting_queue")
db.createUser(
    {
        user: "${MONGO_INITDB_USERNAME}",
        pwd: "$password",
        roles: [
            {
                role: "readWrite",
                db: "${MONGO_INITDB_DATABASE}"
            }
        ]
    }
);
EOF