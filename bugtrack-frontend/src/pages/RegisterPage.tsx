import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { register } from '../api';

export default function RegisterPage() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await register(name, email, password);
      localStorage.setItem('token', res.data.token);
      navigate('/dashboard');
    } catch {
      setError('Registration failed. Email may already be in use.');
    }
  };

  return (
    <div className="auth-container">
      <h2>Create an Account</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Full Name" value={name}
          onChange={e => setName(e.target.value)} required />
        <input type="email" placeholder="Email" value={email}
          onChange={e => setEmail(e.target.value)} required />
        <input type="password" placeholder="Password" value={password}
          onChange={e => setPassword(e.target.value)} required />
        {error && <p className="error">{error}</p>}
        <button type="submit">Register</button>
      </form>
      <p>Already have an account? <Link to="/login">Login</Link></p>
    </div>
  );
}
