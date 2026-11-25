# Diary
#### 11\.
\*\*25 November 2026\*\*
Added delete button to the ui for modules.
made the changes in modulecard.kt

added Assignmententity and Assignmentdao


#### 10\.
\*\*21 November 2026\*\*
Commited modulescreen.kt changes makes the screen more like my figma mock up 

Problem: Couldnt get the emulator to run my updated modulescreen

Resolution: Wiped Emulator data and cold booted it.



Challenge: Designing the AddEditModuleDialog to seamlessly handle both creating a new module and editing an existing one without UI state



To fix this, I used two simple variables in ModuleScreen
A true/false variable (showDialog) to open and close the popup window.
Another variable (selectedModuleForEdit) to keep track of which module the user clicked on.
#### 9\.
\*\*20 November 2026\*\*
Problem: Build failed with Cause: jlink executable ...\.vscode\extensions\... does not exist, caused by Android Studio auto-detecting an experimental Java 25 runtime path 

Resolution: Reconfigured the Gradle JVM criteria in IDE settings

commited module cards to create a cards for module similar to my figma mock up
#### 8\.
\*\*18 November 2026\*\*
added dependencies to improve the modules ui and wrote some unit tests for modules will be working on instrumented tests and on im proving the  modules ui next


#### 7\.
\*\*13 November 2026\*\*
fixed some emulator issues was having trouble running the emulator in Android Studio.

#### 6\.
\*\*9 November 2026\*\*
Wrote Viewmodel and Modulescreen wasnt able to make commits last week because my accounts were locked due to enrollment issues. also made changes to mainactivity.kt to initiated the modules ui also fixed a mistake in ModuleDao which was causing an error.
will be working on the ux personas and ui design next.

#### 5\.
\*\*28 October 2026\*\*
Created ModuleRepository to mediate between viewmodel and ui will help in the future when timetables are imported in to the app. next working on viewmodel and ui 
it does the basic insert delete and update to the database 






#### 4\.

\*\*23 October 2026\*\*
Setup Dao(data access object) and module entity for room data
Implements AppDatabase for sqlite storage on the phone 





#### 3\.

\*\* 21 October 2026 \*\*
Setup dependencies for room database and pushed files. Next, I'll be working on DAO and Entity setup for modules.





#### 2\. 



\*\*19 October 2026\*\*



Set up the Android project in Android Studio using Kotlin and Jetpack Compose and pushed the files to GitLab. Still getting used to Kotlin and Compose. Next, I’ll work on setting up Room for the database.











#### 1\.

*Date:* 15 October 2026


Created the UML use-case diagram for the Workload-Aware Academic Planner and pushed it to GitLab. Started looking into Room for the app's database and continued learning Kotlin and Jetpack Compose. Next, I'll continue setting up the database and start working on module management.
