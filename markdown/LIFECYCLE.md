# [Activity/Fragment Lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)

## onCreate

- when you first start-up your application
- fires when system first creates Activity
- perform basic application startup logic
    - should happen only once for entire life of activity
    - bind data to lists
    - associate activity with a ViewModel
    - instantiate some class-scope variables
- receives savedInstanceState parameter
    - a Bundle object containing activity's previously saved state
    - if activity never existed before, Bundle object is null
- Activity does not reside in Created state and enters Started state

## onStart

- makes activity visible to user
- prepares for activity to enter foreground and become interactive
- initialize code to maintain UI
- in Started state any lifecyle-aware components
    - tied to activity's lifecycle will receive ON_START event
- Activity does not stay resident in Started state and enters Resumed state

## onResume
 
- state in which app interacts with user
- app stays in this state until something happens to take focus away from app
    - i.e., receiving phone call, user navigating to another activity, device screen turning off
- when an interruptive event occurs
    - activity enters Paused state, invoking onPause() callback
- if activity returns to Resumed state from Paused state
    - system calls onResume() again
    - so should implement onResume() to initialize components that were released during onPause()
    - perform any other initializations that must occur each time activity enters Resumed state

## onPause

- system calls this method as first indication that user is leaving activity 
- activity is no longer in foreground
- here is where to: 
    - release system resources
    - release handles to sensors (like GPS)
    - release any resources that may affect battery life while your activity is paused
    - consider doing in onStop for multi-window mode
- when activity becomes completely invisible, system calls onStop()

## onStop

- activity is no longer visible to user
- release/adjust resources that are not needed while app is not visible to user
- perform relatively CPU-intensive shutdown operations
- activity object is kept resident in memory
    - it maintains all state and member information
    - but is not attached to the window manager
    - when activity resumes, activity recalls this information
- if activity comes back, system invokes onRestart()
- if activity is finished running, system calls onDestroy()

## onDestroy

- system invokes this callback either because:
    - activity is finishing
        - due to user completely dismissing activity
        - due to finish() being called on activity
    - system is temporarily destroying activity due to a configuration change
        - such as device rotation or multi-window mode

### Resources

- [Fragment Lifecycle](https://developer.android.com/guide/components/fragments)

