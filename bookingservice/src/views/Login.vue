<template>
  <div class="auth-container">
    <div class="form-container">
      <h2>{{ isSignUp ? 'Sign Up' : 'Sign In' }}</h2>

      <form @submit.prevent="submitForm">
        <div v-if="isSignUp" class="form-group">
          <label for="firstname">First Name</label>
          <input type="text" v-model="form.firstname" id="firstname" required />
        </div>

        <div v-if="isSignUp" class="form-group">
          <label for="lastname">Last Name</label>
          <input type="text" v-model="form.lastname" id="lastname" required />
        </div>

        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" v-model="form.email" id="email" required />
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" v-model="form.password" id="password" required />
        </div>

        <div v-if="isSignUp" class="form-group">
          <label>User Type</label>
          <div>
            <label>
              <input type="radio" value="ADMIN" v-model="form.userType" required /> Admin
            </label>
            <label>
              <input type="radio" value="USER" v-model="form.userType" required /> User
            </label>
          </div>
        </div>

        <button type="submit">{{ isSignUp ? 'Sign Up' : 'Sign In' }}</button>

        <p @click="toggleAuthMode">{{ isSignUp ? 'Already have an account? Sign In' : 'Don\'t have an account? Sign Up' }}</p>
      </form>
    </div>
  </div>
</template>

<script >
import axios from 'axios';
export default {
  data() {
    return {
      isSignUp: false, // Toggle between sign-in and sign-up
      form: {
        firstname: '',
        lastname: '',
        email: '',
        password: '',
        userType: ''
      }
    };
  },
  methods: {
    toggleAuthMode() {
      this.isSignUp = !this.isSignUp;
      this.resetForm();
    },
    // submitForm() {
    //   // Handle form submission here
    //   console.log(this.form);
    //   alert(`${this.isSignUp ? 'Sign Up' : 'Sign In'} form submitted!`);
    // },
    resetForm() {
      this.form = {
        firstname: '',
        lastname: '',
        email: '',
        password: '',
        userType: ''
      };
    },
    async submitForm() {
      console.log("Method Callded")
      if (this.isSignUp) {
        try {
          const response = await axios.post('http://localhost:8080/api/v1/user/addUser', {

            firstname: this.form.firstname,
            lastname: this.form.lastname,
            email: this.form.email,
            password: this.form.password,
            userType: this.form.userType
          });
          alert('User registered successfully!');
          console.log(response.data);
        } catch (error) {
          console.error('Error registering user:', error);
          alert('Failed to register user.');
        }
      } else {
        alert('Sign-in functionality not implemented.');
      }
    }

  }
};
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f2f2f2;
}

.form-container {
  background-color: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  width: 400px;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input[type="text"],
.form-group input[type="email"],
.form-group input[type="password"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-group input[type="radio"] {
  margin-right: 5px;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #45a049;
}

p {
  text-align: center;
  margin-top: 20px;
  cursor: pointer;
  color: #333;
}

p:hover {
  text-decoration: underline;
}
</style>
