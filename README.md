# eTherapists

This is the eTherapists test app created by Anna Berezovskaya. Please, read the following file before starting.

<b>Libraries used</b>

- rx.Observable Java and android library to build a loader with the ability of datacaching and intergrated into the 
activity lifecycle.
- Picasso library to load the picture into the items of RecyclerView. 

All the libraries are declared in build.gradle file as a dependencies and don't require additional 
actions to include into the project.

compile 'io.reactivex:rxjava:1.0.8'
compile 'io.reactivex:rxandroid:0.24.0'
compile 'com.squareup.picasso:picasso:2.5.2'


<b>1. Database Structure</b>
Simplified implementation of Database was created to serve the app's needs. There are several tables:

- BodyPart  -  represents the part of body and contains the name and id.

- Physical Problem - represens the physical problem, selected by user. Unlike a body problem - which is just possible
problem that may happen - physical problem was added by the app user like actual problem.
It contains appropriate body_problem id and the intensity level;

- Body Problem  - represents possible problem for user to be selected. This is potential problem user can see in a list
on the "add problem" dialog. When the intensity and problem selected, the new Physical Problem will be added to database.

- Training  - contains id of body problem and exercise to solve it. Introduced just for decomposition 
of many-to-many relationship between the body problem and exercise essenses. 
(On problem can be solved with different ways, but one exercise can solve a lot of problems as well)

- Exercise - represents exercise - contains title, picture (name of pic in predefined folder in assets) and duration.

Database scheme.png can be found in the root of the project


<b> 2. Comments on resources </b>
After exploring the assets folder I supposed, that resources provided were selected for the xhdpi density. 
It's too small to be xxhdpi, but it's unnecessary to put the image resources under the less resolution as well.
Android will scale resources on the higher density screen, which may cause blurred images
and would influence on user's feeling about the application. 
So, it's better to downscale them. 

For the real product, I'd sudgest to use appropriate resources at least for  xhdpi and xxhdpi densities. 
Instead of it, VectorDrawables, introduced in API 21 may be used to represent a simple icons. Vector images are scale-resistant 
and easy to use with any screen density.

<b>3.Screen orientation </b>
Landscape orientation is not blocked (since this is not a good practice according to Google guidelines). But the app UI,
according to mockups, designed mostly for the portrait orientation. It will be displayed 'as-is' in landscape, no
alternate resources provided

<b>4. Home Screen</b>
I didn't get from mockup, how actually the home screen should look. Since the Splash Activity, according to Google guidelines, is not 
android best practice (I mean useless splash activity, which does not process any actions, but just
displaying an information), I've just implemented the paralax effect on the Application Bar scrolling, which is the most
close to the Home sceen Mockup.

<b>5. Coaching Fragment </b>
The exercises, appropriate to the user-selected body problems, are displayed here, according to mockups.
The 5 random exercises will be displayed. 

Exercises may change every time on data retrieve. 

The displaying the same exercises all the day functionality
may be implemented simpy by using the day of year number as a random generator seed (since the random generator
will give the same sequence for the same seed). For now this is not implemented.

<b>Note</b>, that ORDER BY RAND() SQL statement has a bad performance on big tables.
Since the table of exercises may be big, another approach was used. (the IN {ids}, the list of suitable ids defined before )


<b>6. Body View fragment</b>
Since the picture of the body view is not big and we don't have requirements to show
the click-responce or scale the image, I used the most simple way to solve the problem of clickable areas.
I've made another "map" picture, where every area has another color, and simply put it on the top of the 
body picture (which I merged from pieces provided).
I'm retrieving the click coords, convert it to the colored bitmap coords (which resized together with the original
image by the android system)
and than detect the area of click.

For the bigger images, or images with high requirements for scaling on touch or visual click responce
the custom view with several bitmaps can be used.
But this component requires more time to develop.

Thank you and feel free to ask any questions,
Anna Berezovskaya





