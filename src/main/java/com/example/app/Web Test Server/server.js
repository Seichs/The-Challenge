/*
const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');
const bcrypt = require('bcrypt');
const fs = require('fs');
const app = express();

// Set the port for your server
const PORT = 3000;

// Middleware to parse request body
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// Simulating a database with a JSON file for demo purposes
const dbFilePath = path.join(__dirname, '..', 'users.db'); // Path to the db file

// Serve static files (like your HTML, CSS, JS) from the FrontEnd folder
app.use(express.static(path.join(__dirname, '..', 'FrontEnd')));

// Route for the root path '/' (serving the Login.html page)
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'FrontEnd', 'Login.html'));
});

// Handle registration
app.post('/register', (req, res) => {
    const { username, password, email } = req.body;

    // Check if the username already exists
    if (fs.existsSync(dbFilePath)) {
        const existingUsers = JSON.parse(fs.readFileSync(dbFilePath));
        if (existingUsers.some(user => user.username === username)) {
            return res.status(400).json({ message: 'Username already exists' });
        }
    }

    // Hash the password before storing
    bcrypt.hash(password, 10, (err, hashedPassword) => {
        if (err) return res.status(500).json({ message: 'Error hashing password' });

        // Save the new user to the database
        const newUser = { username, password: hashedPassword, email };
        let users = [];
        if (fs.existsSync(dbFilePath)) {
            users = JSON.parse(fs.readFileSync(dbFilePath));
        }
        users.push(newUser);

        fs.writeFileSync(dbFilePath, JSON.stringify(users));
        res.status(200).json({ message: 'Registration successful' });
    });
});

// Handle login
app.post('/login', (req, res) => {
    const { username, password } = req.body;

    if (!fs.existsSync(dbFilePath)) {
        return res.status(400).json({ message: 'No users found. Please register first.' });
    }

    const users = JSON.parse(fs.readFileSync(dbFilePath));

    // Check if the username exists
    const user = users.find(user => user.username === username);
    if (!user) {
        return res.status(400).json({ message: 'Username does not exist' });
    }

    // Compare the provided password with the stored hashed password
    bcrypt.compare(password, user.password, (err, isMatch) => {
        if (err) return res.status(500).json({ message: 'Error checking password' });

        if (isMatch) {
            return res.status(200).json({ message: 'Login successful' });
        } else {
            return res.status(400).json({ message: 'Invalid password' });
        }
    });
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
*/
