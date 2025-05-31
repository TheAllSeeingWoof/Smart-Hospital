#Smart-Hospital


Project Overview
This is an Android application for a "Smart Hospital" system. It manages users (patients/admins), medical devices, and user medical records (pregledi). The app uses a local SQLite database and communicates with a backend server via HTTP for device management.

Key Components
Activities
MainActivity: Entry point. Handles login, registration, and admin device sync. Admins fetch devices from the server and store them locally. Users are authenticated against the local database.
RegisterActivity: Handles user registration, collects user info, and stores it in the local database.
LoginActivity: After login, loads either the admin or user fragment based on the username.
AddNewDeviceActivity: Allows admins to add new devices, sends device info to the server, and stores it locally.
DeviceActivity: Displays details for a specific device, fetching info from the server.
Fragments
AdminFragment: Shows a list of devices for admins, allows adding devices, and viewing device details. Uses AdminAdapter.
UserFragment: Shows a list of medical records (pregledi) for the logged-in user. Uses UserAdapter.
Adapters
AdminAdapter: Adapter for displaying devices in a list for admins. Allows toggling device state (on/off) and updates both server and local DB.
UserAdapter: Adapter for displaying user medical records in a list.
Database
DBHelper: SQLite helper for managing users, devices, and medical records. Provides CRUD operations and ID generation.
Models
Korisnik: Represents a user (patient/admin) with personal info and credentials.
adminUredjaj: Represents a device with name, image, state, and ID.
User_pregledi: Represents a user's medical record with date, name, and ID.
HTTP
HttpHelper: Handles HTTP GET, POST, and DELETE requests to the backend server for device management.
Service
MyService: Android Service for background operations (not fully implemented).
Binder: AIDL-based binder for inter-process communication, periodically fetches devices from the server and updates the local DB.
How it Works
Login/Registration: Users can register and log in. Admins have special privileges.
Device Management: Admins can add devices, toggle their state, and sync with the backend. Device info is stored both locally and remotely.
User Records: Users can view their medical records, which are stored locally.
Fragments/Adapters: UI is split into fragments for admin/user roles, with adapters for displaying lists.
Notable Features
Uses both local SQLite and remote HTTP API for data management.
Role-based UI (admin vs. user).
Device state can be toggled and is synced with the backend.
Modular code with clear separation between activities, fragments, adapters, models, and helpers.
