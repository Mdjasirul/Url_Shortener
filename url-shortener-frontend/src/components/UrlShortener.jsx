import { useState } from "react";
import { shortenUrlApi } from "../api";
import "../styles/UrlShortener.css";

const UrlShortener = () => {
  const [longUrl, setLongUrl] = useState("");
  const [customAlias, setCustomAlias] = useState("");
  const [expiryMinutes, setExpiryMinutes] = useState("");
  const [shortUrl, setShortUrl] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setShortUrl("");

    try {
      const result = await shortenUrlApi({
        longUrl,
        customAlias: customAlias || null,
        expiryMinutes: expiryMinutes || null,
      });
      setShortUrl(result);
    } catch (err) {
      setError("Unable to shorten URL");
    }
  };

  return (
    <div className="container mt-5">
      <div className="card shadow p-4">
        <h3 className="text-center mb-4">🔗 URL Shortener</h3>

        <form onSubmit={handleSubmit}>
          <input
            className="form-control mb-3"
            placeholder="Enter long URL"
            value={longUrl}
            onChange={(e) => setLongUrl(e.target.value)}
            required
          />

          <input
            className="form-control mb-3"
            placeholder="Custom alias (optional)"
            value={customAlias}
            onChange={(e) => setCustomAlias(e.target.value)}
          />

          <input
            className="form-control mb-3"
            type="number"
            placeholder="Expiry minutes (optional)"
            value={expiryMinutes}
            onChange={(e) => setExpiryMinutes(e.target.value)}
          />

          <button className="btn btn-primary w-100">
            Shorten URL
          </button>
        </form>

        {shortUrl && (
          <div className="alert alert-success mt-4 text-center">
            <strong>Short URL:</strong>
            <br />
            <a href={shortUrl} target="_blank" rel="noreferrer">
              {shortUrl}
            </a>
          </div>
        )}

        {error && (
          <div className="alert alert-danger mt-3 text-center">
            {error}
          </div>
        )}
      </div>
    </div>
  );
};

export default UrlShortener;
