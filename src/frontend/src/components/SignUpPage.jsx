import '../App.css';
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function SignUpPage() {
    const navigate = useNavigate();

    useEffect( () => {
        const user=localStorage.getItem('user');
        if(user){
            navigate('/');
            }
        },
        []);
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [gender, setGender] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const [password, setPassword] = useState('');

    const [usernameError, setUsernameError] = useState('');
    const [emailError, setEmailError] = useState('');
    const handleSubmit = async (e) => {
        e.preventDefault();

        setUsernameError('');
        setEmailError('');
        const userRequest = {username:username,
            email: email,
            gender:gender,
            birthDate: birthDate,
            password: password};


        try{
            const response= await fetch('http://localhost:8080/users', {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json'
                    },
                body: JSON.stringify(userRequest)
                });
            if(response.ok){
                navigate('/sign-in');
            }else{
                const errorJson=await response.json();
                const backendMessage = (errorJson.message || "").toLowerCase();
                if(backendMessage.includes("username already exists")){
                    setUsernameError("This username is already taken.");
                }else if(backendMessage.includes("email already exists")){
                    setEmailError("This email is already registered");
                }else{
                    console.error(backendMessage||"Failed to create account. Check your data.");
                }
            }
        }catch(error){
            console.error("Server is unreachable. Please try again later." + error);
        }
    };

    return (
        <div className="auth-page-container">
            <div style={{ marginBottom: '24px', cursor: 'pointer' }} onClick={() => navigate("/")}>
                 <img src="./images/light-logo.png" alt="Logo" style={{ height: '150px'}}/>
            </div>

            <div className="auth-card">
                <h1>Create account</h1>

                <form onSubmit={handleSubmit} className="auth-form">
                    <div className="auth-input-group">
                        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Enter username" className="auth-input" required />
                        {usernameError && <p style={{ color: 'red', fontSize: '12px', marginTop: '4px' }}>{usernameError}</p>}
                    </div>

                    <div className="auth-input-group">
                        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Enter Email" className="auth-input" required />
                        {emailError && <p style={{ color: 'red', fontSize: '12px', marginTop: '4px' }}>{emailError}</p>}
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