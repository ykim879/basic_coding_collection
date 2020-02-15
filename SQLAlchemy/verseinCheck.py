from sqlalchemy import Column, Integer, String, Float
class GTStudent(Base):
	__tablename__ = 'GTStudent'

	gtid = Column(Integer, primary_key=True)
	name = Column(String)
	graduateYear = Column(Integer)
	gpa = Column(Float)

	def __repr__(self):
		return "Student info: \n\tGTid: %d \n\tname: %s\n\tExpected graduate year: %d\n\tCurrent GPA: " 
		% (self.gtid, self.name, self.graduateYear, self.gpa)