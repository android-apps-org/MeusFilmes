# Background Tasks

## Services

- Component meant for running long running tasks
  - that do not need a visual component
  - great for loading and processing data in background
- Example: get emails, texts, calendar reminders
  - even when these apps are closed
  - background services are running on phone listening for and downloading data
- Starting Services:
  - Manually Start Service
    - call startService() from context (Activity)
    - no communication back
  - Schedule a Service
    - JobService -> run by JobScheduler
  - Bind to a Service
    - client-server like interface
    - Service acts as server
    - Components binding to service act as client
      - i.e. Activity -> bindService()
      - can communicate back/forth
        - i.e. media player app
          - bound service plays audio
          - activity controls UI

## Loaders vs Services

### Loaders Use Cases:

- Background task is loading information only used in Activity
  - i.e. decoding image to use in ImageView
  - or querying database to populate recyclerview adapter
  - network case: app is inherently real-time
    - fetch as needed in ui rather than cache data in database
- Easy to make UI changes and communicate with Activity

### Services Use Cases:

- Task is decoupled from UI
  - i.e. updating database in background
- Exists even if there is no UI


## Resources

- [Bound Service](https://developer.android.com/guide/components/bound-services.html)


