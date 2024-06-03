<?php
include 'config.php';

header("Content-Type: application/json");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $oldUsername = $_POST['old_username'];
    $newUsername = isset($_POST['new_username']) ? $_POST['new_username'] : null;
    $newEmail = isset($_POST['email']) ? $_POST['email'] : null;
    $newPassword = isset($_POST['password']) ? $_POST['password'] : null;

    $query = "SELECT * FROM users WHERE username = ?";
    $stmt = mysqli_prepare($conn, $query);
    mysqli_stmt_bind_param($stmt, "s", $oldUsername);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    if ($result && mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);

        $updates = [];
        $params = [];
        $types = "";

        if ($newUsername) {
            $updates[] = "username = ?";
            $params[] = $newUsername;
            $types .= "s";
        } else {
            $newUsername = $row['username'];
        }

        if ($newEmail) {
            $updates[] = "email = ?";
            $params[] = $newEmail;
            $types .= "s";
        } else {
            $newEmail = $row['email'];
        }

        if ($newPassword) {
            $hashedPassword = password_hash($newPassword, PASSWORD_DEFAULT);
            $updates[] = "password = ?";
            $params[] = $hashedPassword;
            $types .= "s";
        } else {
            $hashedPassword = $row['password'];
        }

        if (count($updates) > 0) {
            $updateString = implode(", ", $updates);
            $sql = "UPDATE users SET $updateString WHERE username = ?";
            $stmt = mysqli_prepare($conn, $sql);
            $params[] = $oldUsername;
            $types .= "s";
            mysqli_stmt_bind_param($stmt, $types, ...$params);

            if (mysqli_stmt_execute($stmt)) {
                echo json_encode(['status' => 'success', 'message' => 'User info updated successfully']);
            } else {
                echo json_encode(['status' => 'error', 'message' => 'Error updating user info: ' . mysqli_error($conn)]);
            }
        } else {
            echo json_encode(['status' => 'error', 'message' => 'No updates to be made']);
        }
    } else {
        echo json_encode(['status' => 'error', 'message' => 'User not found']);
    }

    mysqli_close($conn);
}
