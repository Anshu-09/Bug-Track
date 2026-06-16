import { useNavigate } from 'react-router-dom';

export default function LandingPage() {
  const navigate = useNavigate();

  return (
    <div className="landing">

      {/* Navbar */}
      <nav className="landing-nav">
        <span className="landing-logo">🐛 Bug Track</span>
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
