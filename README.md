# MusicHub --Java-Playlist--
That's a project me and my colleague @MaryVou have done. The Java Application draws JSONObjects from MusicBrainz Database and objectifies the results.
There is also an option for the developer to add his/her personal database in the Database class.
Then, the user can login, sign up and save favorite songs and artists to his/her profile.
In guest mode, the app draws JSONObjects only from the MusicBrainz API.
When a user signs up,every search result from MusicBrainz, will automatically be saved in the database.
The feature of saving favorite songs/artists is only available when the developer has connected the app with a database.
If the connection with the database can't be achieved, a window shows up, arging the user either use guest mode or exit.
The script for the Oracle(!) database can be found in oracleDatabase.txt.
