import sys, csv
from StringIO import StringIO

class Creator:
	def __init__(self, argv):
		self.col_with_name = 1
		self.col_with_length =3
		self.col_with_comment = None
		self.col_with_flag = 2
		self.col_with_tab_name = 0

		self.argv = argv

	def createEntityFile(self, a_csv_string):
		fieldType=  {'L': 'BigDecimal', 'N': 'Long', 'D': 'Date', 'A': 'String'}
		positionDictionary = {'class': self.col_with_tab_name, 'type': self.col_with_flag, 'field': self.col_with_name}
		structureDictionary = {'class': 'public class {0} \n{{\n', 'field': '\tprivate {0} {1};\n\n', 'end': '}', 'suffix': '.java'}
		annotationDictionary = {'class': '@Entity\n@Table(name = {0})\n', 'field': '\t@Column(name = "{0}")\n'}

		return self.createBody(a_csv_string, fieldType, positionDictionary, structureDictionary, annotationDictionary = annotationDictionary)

	def createBody(self, a_csv_string, aFieldDictionary, aPositionDictionary, aStructureDictionary, **kwargs):
		aAnnotationDictionary = None
		formatField = None
		formatAnnotation = None
		for k, v in kwargs.iteritems():
			if k == "annotationDictionary":
				aAnnotationDictionary = v
			if k == "format":
				formatField = v
			if k == "formatAnnotation":
				formatAnnotation = v

		body = ''

		isOpen = False

		buff = StringIO(a_csv_string)

		content = csv.reader(buff, delimiter=";")

		for row in content:
			classNamePos = row[aPositionDictionary['class']].strip()
			fieldTypePos = row[aPositionDictionary['type']].strip()
			fieldNamePos = row[aPositionDictionary['field']].strip()

			if classNamePos != '':

				if isOpen:
					body += aStructureDictionary['end']
					isOpen = False

				isOpen = True

				body += '\n\n'

				if aAnnotationDictionary is not None:
					body += aAnnotationDictionary['class'].format(classNamePos)
				body += aStructureDictionary['class'].format(classNamePos)

			if isOpen == True and fieldNamePos != '' and fieldTypePos != '':
				if aAnnotationDictionary is not None:
					if formatAnnotation is not None: # format annotation field for example to upper
						body += aAnnotationDictionary['field'].format(formatAnnotation(fieldNamePos))
					else:
						body += aAnnotationDictionary['field'].format(fieldNamePos)

				if formatField is not None: # format field for example to upper
					body += aStructureDictionary['field'].format(aFieldDictionary[fieldTypePos], formatField(fieldNamePos))
				else:
					body += aStructureDictionary['field'].format(aFieldDictionary[fieldTypePos], fieldNamePos)

		body += aStructureDictionary['end']

		return body