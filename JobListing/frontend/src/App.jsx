import { useEffect, useState } from "react";

function App() {
  const [jobs, setJobs] = useState([]);

  const [jobId, setJobId] = useState("");
  const [profile, setProfile] = useState("");
  const [description, setDescription] = useState("");
  const [experience, setExperience] = useState("");
  const [techstack, setTechstack] = useState("");

  const loadJobs = () => {
    fetch("http://localhost:8085/jobs")
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setJobs(data);
      })
      .catch((error) => console.error(error));
  };

  useEffect(() => {
    loadJobs();
  }, []);

  const addJob = async () => {
    const newJob = {
      jobId: Number(jobId),
      profile: profile,
      description: description,
      experience: Number(experience),
      techstack: techstack.split(",").map((item) => item.trim())
    };

    try {
      const response = await fetch(
        "http://localhost:8085/addJobs",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(newJob)
        }
      );

      const savedJob = await response.json();

      console.log("Saved:", savedJob);

      loadJobs();

      setJobId("");
      setProfile("");
      setDescription("");
      setExperience("");
      setTechstack("");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Job Portal</h1>

      <h2>Add Job</h2>

      <input
        type="number"
        placeholder="Job ID"
        value={jobId}
        onChange={(e) => setJobId(e.target.value)}
      />

      <br />
      <br />

      <input
        type="text"
        placeholder="Profile"
        value={profile}
        onChange={(e) => setProfile(e.target.value)}
      />

      <br />
      <br />

      <input
        type="text"
        placeholder="Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />

      <br />
      <br />

      <input
        type="number"
        placeholder="Experience"
        value={experience}
        onChange={(e) => setExperience(e.target.value)}
      />

      <br />
      <br />

      <input
        type="text"
        placeholder="Tech Stack (comma separated)"
        value={techstack}
        onChange={(e) => setTechstack(e.target.value)}
      />

      <br />
      <br />

      <button onClick={addJob}>Add Job</button>

      <hr />

      <h2>Available Jobs</h2>

      {jobs.map((job, index) => (
        <div
          key={job.jobId || index}
          style={{
            border: "1px solid black",
            padding: "10px",
            marginBottom: "10px",
            borderRadius: "5px"
          }}
        >
          <h3>{job.profile}</h3>

          <p>{job.description}</p>

          <p>
            <strong>Experience:</strong> {job.experience} years
          </p>

          <p>
            <strong>Tech Stack:</strong>{" "}
            {job.techstack?.join(", ")}
          </p>
        </div>
      ))}
    </div>
  );
}

export default App;