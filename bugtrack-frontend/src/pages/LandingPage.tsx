import { useNavigate } from 'react-router-dom';

export default function LandingPage() {
  const navigate = useNavigate();

  return (
    <div className="landing">

      {/* Navbar */}
      <nav className="landing-nav">
        <span className="landing-logo">
          <svg width="22" height="22" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" style={{marginRight: '8px', verticalAlign: 'middle'}}>
            <circle cx="12" cy="13" r="6" fill="#2da44e"/>
            <ellipse cx="12" cy="10" rx="3" ry="2" fill="#2da44e"/>
            <line x1="8" y1="8" x2="5" y2="5" stroke="#2da44e" strokeWidth="1.5" strokeLinecap="round"/>
            <line x1="16" y1="8" x2="19" y2="5" stroke="#2da44e" strokeWidth="1.5" strokeLinecap="round"/>
            <line x1="6" y1="12" x2="3" y2="11" stroke="#2da44e" strokeWidth="1.5" strokeLinecap="round"/>
            <line x1="18" y1="12" x2="21" y2="11" stroke="#2da44e" strokeWidth="1.5" strokeLinecap="round"/>
            <line x1="6" y1="15" x2="3" y2="16" stroke="#2da44e" strokeWidth="1.5" strokeLinecap="round"/>
            <line x1="18" y1="15" x2="21" y2="16" stroke="#2da44e" strokeWidth="1.5" strokeLinecap="round"/>
            <circle cx="10" cy="13" r="1" fill="white"/>
            <circle cx="14" cy="13" r="1" fill="white"/>
          </svg>
          Bug Track
        </span>
        <div className="landing-nav-links">
          <button className="btn-secondary" onClick={() => navigate('/login')}>Login</button>
          <button onClick={() => navigate('/register')}>Get Started</button>
        </div>
      </nav>

      {/* Hero */}
      <section className="landing-hero">
        <h1>Track bugs.<br />Ship faster.</h1>
        <p>Bug Track is a collaborative issue tracking tool for software teams. Post bugs, assign them to teammates, and resolve them with a GitHub branch link — all in one place.</p>
        <div className="landing-hero-actions">
          <button onClick={() => navigate('/register')}>Get Started for Free</button>
          <button className="btn-secondary" onClick={() => navigate('/login')}>Login</button>
        </div>
      </section>

      {/* Features */}
      <section className="landing-features">
        <h2>Everything your team needs</h2>
        <div className="features-grid">
          <div className="feature-card">
            <span>🔐</span>
            <h3>Secure Auth</h3>
            <p>JWT-based authentication with BCrypt password hashing. Your data stays safe.</p>
          </div>
          <div className="feature-card">
            <span>📬</span>
            <h3>Invite Codes</h3>
            <p>Share a 6-character invite code with your team. No admin approval needed.</p>
          </div>
          <div className="feature-card">
            <span>🐛</span>
            <h3>Issue Tracking</h3>
            <p>Post bugs with severity levels — LOW, MEDIUM, HIGH. Sort and search instantly.</p>
          </div>
          <div className="feature-card">
            <span>⚡</span>
            <h3>Status Workflow</h3>
            <p>Issues move from OPEN → IN_PROGRESS → RESOLVED with validated transitions.</p>
          </div>
          <div className="feature-card">
            <span>🔗</span>
            <h3>GitHub Integration</h3>
            <p>Link your GitHub branch when resolving an issue. Full traceability for every fix.</p>
          </div>
          <div className="feature-card">
            <span>📋</span>
            <h3>Activity Feed</h3>
            <p>Every action is logged. Your team sees the 20 most recent activities in real time.</p>
          </div>
        </div>
      </section>

      {/* How it works */}
      <section className="landing-steps">
        <h2>How it works</h2>
        <div className="steps-list">
          <div className="step"><span>1</span><p>Register and create a project</p></div>
          <div className="step"><span>2</span><p>Share the 6-char invite code with your team</p></div>
          <div className="step"><span>3</span><p>Post issues with title, description, and severity</p></div>
          <div className="step"><span>4</span><p>Assign issues to yourself and start working</p></div>
          <div className="step"><span>5</span><p>Paste your GitHub branch link and mark resolved</p></div>
        </div>
      </section>

      {/* CTA */}
      <section className="landing-cta">
        <h2>Ready to squash some bugs?</h2>
        <button onClick={() => navigate('/register')}>Create a Free Account</button>
      </section>

      {/* Footer */}
      <footer className="landing-footer">
        <p>Built with Java 17, Spring Boot 3, React + TypeScript · 🐛 Bug Track</p>
      </footer>

    </div>
  );
}
