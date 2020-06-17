# mindvalley
1. What parts of the test did you find challenging and why?
There are some challenges:
- The data is returned by the server miss some fields (like id). It is difficult to check whether a data object that is returned by the server and a data in database is the same or not.
I guess you deleted them to make the challenge is more difficult.
- The image is returned by the server in square, design want to show all of images in rectangle. It is a challenge for me.


2. What feature would you like to add in the future to improve the
   project?
   - Before saving the new data to database, I delete all of the old data. So the first thing I want to improve the project is
   make a updating solution. It means the app will check whether a data object is changed or not, If it changed, it will be updated to database.
   - I also want to add JobManager and Job to this project for saving data to database. Currently, after getting data from server I saved it to data in the thread.
   - Write UI test for fragment and component.
  
