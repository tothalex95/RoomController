// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

exports.saveMeasurement = functions.https.onRequest((request, response) => {
    const measurement = {
        humidity: request.body.humidity,
        temperature: request.body.temperature
    };

    return admin.database().ref('/measurements')
        .push({
            measurement: measurement
        })
        .then(snapshot => {
            return response.send({
                url: snapshot.ref.toString()
            });
        });
});

exports.sendNewMeasurementsNotification = functions.database.ref('/measurements/{pushId}/measurement')
    .onCreate((snapshot, context) => {
        const measurement = snapshot.val();

        const payload = {
            notification: {
                title: 'New measurement',
                body: 'A new measurement is available.'
            },
            data: {
                humidity: measurement.humidity.toString(),
                temperature: measurement.temperature.toString()
            }
        };

        const options = {
            priority: 'high',
            timeToLive: 60 * 60 * 2
        };

        admin.messaging().sendToTopic('measurements', payload, options);
    });

exports.setServoPosition = functions.https.onRequest((request, response) => {
    const servoPosition = request.body.servoPosition;

    admin.database().ref('/servoPosition').push({
        servoPosition: servoPosition
    });

    return admin.database().ref('/servoPositions')
        .push({
            servoPosition: servoPosition
        })
        .then(snapshot => {
            return response.send({
                url: snapshot.ref.toString()
            })
        })
});