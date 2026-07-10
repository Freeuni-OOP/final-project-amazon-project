import { useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';

function SignInPage() {
  const navigate = useNavigate();

    useEffect( () => {
        const user=localStorage.getItem('user');
        if(user){
            navigate('/');
            }
        },
        []);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const handleSubmit=async(event) => {
    event.preventDefault();
    setErrorMsg('');

    const signInRequest={email: email, password: password};

    try{
        const response= await fetch('http://localhost:8080/users/sign-in', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(signInRequest)});
            if(response.ok){
                const userData=await response.json();
                localStorage.setItem('user', JSON.stringify(userData));
                navigate('/');
            }else{
                setErrorMsg("Invalid email or password. Try again.");
            }
    }catch(error){
        console.error("Connection error", error);
    }
  }

  return (
      <div className="auth-page-container">
          <div style={{ marginBottom: '24px', cursor: 'pointer' }} onClick={() => navigate("/")}>
              <img src="/images/light-logo.png" alt="Logo" style={{ height: '150px' }}/>
          </div>

          <div className="auth-card">
              <h1>Sign in</h1>
              {errorMsg && <p style={{ color: 'red', textAlign: 'center', marginBottom: '15px', fontSize: '14px' }}>{errorMsg}</p>}
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