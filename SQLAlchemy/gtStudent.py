from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, Float
from sqlalchemy.orm import sessionmaker

session = sessionmaker()
engine = create_engine('sqlite:///:memory:', echo=True)
Base = declarative_base()
session.configure(bind=engine) #session = sessionmaker(bind=engine)

class GTStudent(Base):
	__tablename__ = 'GTStudent'

	gtid = Column(Integer, primary_key=True)
	name = Column(String)
	graduateYear = Column(Integer)
	gpa = Column(Float)

	def __repr__(self):
		return "Student info: \n\tGTid: %d \n\tname: %s\n\tExpected graduate year: %d\n\tCurrent GPA: " \
		% (self.gtid, self.name, self.graduateYear, self.gpa)

Base.metadata.create_all(engine)
yiyeon = GTStudent(gtid = 903550379, name = 'Yiyeon', graduateYear = 2021, gpa = 4.0)