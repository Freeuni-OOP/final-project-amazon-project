import '../App.css';
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function SignUpPage() {
    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [gender, setGender] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const userDTO = { username, email, gender, birthDate, password };
    };

    return (
        <div className="auth-page-container">
            <div style={{ marginBottom: '24px', cursor: 'pointer' }} onClick={() => navigate("/")}>
                 <img src="/images/light-logo.png" alt="Logo" style={{ height: '150px', objectFit: 'contain' }}/>
            </div>

            <div className="auth-card">
                <h1>Create account</h1>

                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="auth-input-group">
                        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Enter username" className="auth-input" required />
                    </div>

                    <div className="auth-input-group">
                        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Enter Email" className="auth-input" required />
                    </div>

                    <div className="auth-input-group">
                        <select value={gender} onChange={(e) => setGender(e.target.value)} className="auth-input" required>
                            <option value="">Select Gender</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="OTHER">Other</option>
                        </select>
                    </div>

                    <div className="auth-input-group">
                        <input type="date" value={birthDate} onChange={(e) => setBirthDate(e.target.value)} className="auth-input" required />
                    </div>

                    <div className="auth-input-group">
                        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Enter password" className="auth-input" required />
                    </div>

                    <button type="submit" className="auth-btn">Sign Up</button>
                </form>

                <p className="auth-footer-text">
                    Already have an account?{' '}
                    <span onClick={() => navigate('/sign-in')} className="auth-link">
                        Sign In
                    </span>
                </p>
            </div>
        </div>
    );
}

export default SignUpPage;