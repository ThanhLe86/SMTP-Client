import tkinter as tk

window = tk.Tk()
window.geometry("400x300")
window.title("Login Screen")

def login():
    email = email_entry.get()
    password = password_entry.get()
    if email and password:
        # Lead to another screen
        # Add your code here
        # For example, you can create a new window or switch frames
        # to display the next screen
        new_screen = tk.Toplevel(window)
        new_screen.title("Welcome")
        # Add your widgets and layout for the new screen here
    else:
        # Display an error message
        # Add your code here
        error_label.config(text="Please enter both email and password.")

# Create login button
login_button = tk.Button(window, text="Login", command=login)
login_button.pack()

# Create email and password entry fields
email_label = tk.Label(window, text="Email:")
email_label.pack()
email_entry = tk.Entry(window)
email_entry.pack()

password_label = tk.Label(window, text="Password:")
password_label.pack()
password_entry = tk.Entry(window, show="*")
password_entry.pack()

# Create error label
error_label = tk.Label(window, fg="red")
error_label.pack()

# Start the main loop
window.mainloop()