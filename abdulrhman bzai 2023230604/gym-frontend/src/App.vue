<script setup lang="ts">
const year = new Date().getFullYear()
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-dark navbar-gym mb-4">
    <div class="container">
      <RouterLink class="navbar-brand" to="/">
        <i class="fa-solid fa-dumbbell"></i> Gym Manager
      </RouterLink>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav"
        aria-controls="mainNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="mainNav">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <RouterLink class="nav-link" exact-active-class="active" to="/">
              <i class="fa-solid fa-house"></i> Roster
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" active-class="active" to="/members">
              <i class="fa-solid fa-users"></i> Members
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" active-class="active" to="/trainers">
              <i class="fa-solid fa-user-tie"></i> Trainers
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" active-class="active" to="/plans">
              <i class="fa-solid fa-tags"></i> Price list
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" active-class="active" to="/about">
              <i class="fa-solid fa-circle-info"></i> About
            </RouterLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <main class="container">
    <RouterView />
  </main>
  <footer class="text-center">
    &copy; {{ year }} Gym Manager - Singidunum University
  </footer>
</template>
