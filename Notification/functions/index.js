const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

exports.notification=functions.database.ref('/Group/{subGp}/{id}').onCreate((snap,context) => {
    const groupName=context.params.subGp;
    console.log("groupName",groupName);

    const root=snap.val();
    const date=root.date;
    const message=root.message;
    const name=root.name;
    const tagClientName=root.tagClientName;
    const sub=root.sub;
    const time=root.time;

     console.log("name",root.name);

    const payload={
      data:{
        groupName:groupName,
        date:date,
        message:message,
        name:name,
        tagClientName:tagClientName,
        sub:sub,
        time:time
      }
    }

    return admin.messaging().sendToTopic(groupName.toString(),payload)
    .catch(error=>{
      console.log(error);
    })
  });