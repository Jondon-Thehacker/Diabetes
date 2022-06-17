-- Creating appropriate indexing and hashing
create index patientIndex on patient(patient_Id) using hash;
create index doctorIndex on doctor(doctor_Id) using hash;
create index notesIndex on notes(patient_Id) using hash;
create index measurementIndex on measurement(time) using btree;