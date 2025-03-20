# Art Gallery

## Overview
Art Gallery is a platform where users can upload and share their artwork with others. The application provides user authentication, category management by admins, and a secure environment using JWT authentication.

## Key Features

### User Features:
- Users can upload their artwork to share with others.
- Users can create an account, and a verification email will be sent.
- Users can log in and view their profile.
- Users can see other people's profiles.
- Users can post their artwork for public viewing.
- Anyone can explore and view the artwork uploaded by others.

### Admin Features:
- Admins can upload and manage categories for artworks.

### Security & Design:
- JWT authentication is implemented to ensure secure user tracking.
- Responsive web design for a seamless experience across different devices.

## Future Features (Planned):
- Users will be able to comment and react to other people's artwork.
- Users will have the ability to sell their artwork to others.

## Technologies Used
- **Frontend:** (Angular, Bootstrap, etc.)
- **Backend:** (Spring Boot)
- **Database:** (MySQ)
- **Security:** JWT Authentication

## Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/fmahadyBD/art-gallery.git
   ```
2. Navigate to the project directory:
   ```sh
   cd art-gallery
   ```
3. Install dependencies:
   ```sh
   npm install  # For frontend
   mvn clean install  # For Spring Boot backend
   ```
4. Configure the database and environment variables.
5. Run the backend server:
   ```sh
   mvn spring-boot:run
   ```
6. Run the frontend application:
   ```sh
   npm start
   ```
## Screenshorts of this project:

## License
This project is licensed under the [MIT License](LICENSE).
