Company Directory
=================

Company directory is a telephony application that let callers connect to company phones by keying an extension.

Requirements
-------------

- [Google App Engine (Java)](https://developers.google.com/appengine/docs/java/)
- [Hoiio Developer Account](http://developer.hoiio.com/)


Setup GAE
---------

Setup GAE Java SDK and Eclipse Plugin by following this [Google guide](https://developers.google.com/appengine/docs/java/gettingstarted/installing).

Setup a new GAE application from [http://appspot.com](http://appspot.com). Thanks to Google, it's free to setup a web app on their cloud, and probably free for serving this lightweight app. 

Note down the `Appspot Identifier` that you specify when setting up your GAE application.


Setup Hoiio
-----------

You need to register a Hoiio Account at http://developer.hoiio.com/.

With an account, login to Hoiio, create an app and note down your `Hoiio App ID` and `Hoiio Access Token`.

Then go to Numbers and purchase a number of your choice.

Configure the number and change the Voice Notify URL to http://your-appspot-identifier.appspot.com/in/answer. Replace with your `Appspot Identifier`.

Note: A free Hoiio account has trial restrictions. You would need to top up Hoiio credits to lift the trial.


Setup Project
-------------

Clone this project:

	git clone https://github.com/Hoiio/company-directory.git

In Eclipse, **File** > **Import** > **Existing Projects into Workspace**. Select the cloned directory and click **Finish**.

You will need to Edit 2 files:

In `/src/com/hoiio/api/extensions/persistence/Configuration.java` 

- Replace `appId` and `accessToken` with yours
- Replace `appSpotUrl` with yours 
- Change the number of digits that each extension has

In `/war/WEB-INF/appengine-web.xml`

- Replace `your-appspot-identifier` with yours

Deploy the project. From Eclipse, right click on the project, **Google** > **Deploy to App Engine**. 

If the console says successful, you're ready to configure your company directory!



Configure
----------

Go to your appspot URL eg. http://your-appspot-identifier.appspot.com/

You have to login with the Google Account that created the GAE app. That Google Account is the admin, and is the only person who can edit the company directory.

After logging in, use the interface to map your company extensions to phone numbers. 

Test out by calling your Hoiio Number!
