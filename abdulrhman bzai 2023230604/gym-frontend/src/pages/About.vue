<template>
    <div class="crud-wrapper">
        <div class="card">
            <div class="card-body">
                <h1 class="page-title">About this project</h1>
                <p>
                    Gym Manager is a project for the course Internet Technologies and Web Services
                    at Singidunum University.
                </p>
                <p>
                    It is used by the gym's front desk staff and manages three related tables:
                    <strong>membership plans</strong> (the price list), <strong>trainers</strong> and
                    <strong>members</strong>.
                </p>
                <ul>
                    <li>Every member has exactly one plan (<code>members.plan_id</code>).</li>
                    <li>A member can have one trainer or train alone (<code>members.trainer_id</code>, can be empty).</li>
                    <li>The price is the plan price plus the trainer's monthly fee for every month of the plan.</li>
                </ul>
                <h2 class="h4 mt-4">Technologies</h2>
                <ul>
                    <li>Backend: Java 17, Spring Boot, Spring Data JPA, MySQL</li>
                    <li>Frontend: Vue 3, TypeScript, Vue Router, Axios, Bootstrap, SweetAlert2</li>
                </ul>
                <h2 class="h4 mt-4">Student:</h2>
                <p class="mb-0">Abdulrhman Bzai, index number 2023230604</p>
            </div>
        </div>
    </div>
</template>
