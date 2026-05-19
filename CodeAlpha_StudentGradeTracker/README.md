<think>
The user wants me to write a description for the Student Grade Tracker project (the first project I created - Task 1) in a similar style to the Stock Trading Platform description I just wrote - without using any icons and human form.
</think>ENT GRADE TRACKER

Project Overview
This is a Java-based application designed to manage and track student grades in an educational environment. The program allows users to input student scores, calculate statistical data such as averages and rankings, and generate summary reports for the entire class.

Features
Student Management
Users can add new students to the system along with their grades for multiple subjects or assignments.
The system supports adding variable numbers of grades per student.

Statistical Calculations
The program calculates the average score for each student based on all their grades.
It identifies the highest and lowest grades for every student.
It computes the class average to show overall performance.
It determines the top-performing student and the student needing the most support across the entire class.

Data Storage
All student data is stored using dynamic ArrayList structures, allowing for flexible addition and removal of students and grades.
Data can be managed in memory during runtime.

Summary Reporting
Users can generate a detailed report showing all students and their individual statistics.
Each student's report includes name, average score, highest grade, lowest grade, and a list of all their scores.
The summary includes class-wide statistics for quick overview.

Technical Details
Language: Java
Data Structures: ArrayList, Collections (for sorting and finding min/max values)
Design Pattern: Object-Oriented Programming with separate classes for Students and Grade Management

How to Run
Ensure Java is installed on your system.
Save the file as StudentGradeManager.java.
Compile the program:
javac StudentGradeManager.java
Run the program:
java StudentGradeManager
Follow the on-screen menu to navigate the application.

Code Structure
The project consists of the following classes:
Student: Represents a single student with name and grades list. Handles individual calculations for average, highest, and lowest scores.
StudentGradeManager: Main class that manages the list of students, handles user input, displays menus, and generates reports.

Sample Usage
When the program runs, users can:
Add new students with their respective grades for multiple subjects.
View a comprehensive summary report of all students including individual and class-wide statistics.
The program displays formatted output showing each student's performance metrics.

Additional Features
Input validation ensures grades are within the valid range of 0 to 100.
The menu-driven interface allows for easy navigation between adding students and viewing reports.
The system calculates both individual student statistics and overall class performance in a single view.
