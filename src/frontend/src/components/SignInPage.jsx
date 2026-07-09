import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function SignInPage() {
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  function handleSubmit(event) {
    event.preventDefault();
  }

  return (
      <div className="auth-page-container">
          <div style={{ marginBottom: '24px', cursor: 'pointer' }} onClick={() => navigate("/")}>
              <img src="/images/light-logo.png" alt="Logo" style={{ height: '150px', objectFit: 'contain' }}/>
          </div>

          <div className="auth-card">
              <h1>Sign in</h1>
              <form onSubmit={handleSubmit} className="auth-form">
                  <div className="auth-input-group">
                      <input type="email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      placeholder="Enter Email" className="auth-input" required/>
                  </div>

                  <div>
                      <input type="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      placeholder="Enter password" className="auth-input" required
                      />
                  </div>
                  <button type="submit" className="auth-btn">Sign In</button>
              </form>

              <p className="auth-footer-text">
                  Don't have an account?{' '}
                  <span onClick={() => navigate('/sign-up')} className="auth-link">
                      Sign Up
                  </span>
              </p>
          </div>

      </div>
      );
}

export default SignInPage;