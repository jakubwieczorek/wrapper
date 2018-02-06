from CreatorUtils import *
from StringIO import StringIO
import csv

class Creator:
	def __init__(self, argv):
		self.col_with_name = 1
		self.col_with_length =3
		self.col_with_comment = None
		self.col_with_flag = 2 # type string etc.
		self.col_with_tab_name = 0

		self.argv = argv

	def createEntityFile(self, a_csv_string):
		fieldType = {'L': 'BigDecimal', 'N': 'Long', 'D': 'Date', 'A': 'String'}
		positionDictionary = {'class': self.col_with_tab_name, 'type': self.col_with_flag, 'field': self.col_with_name}
		structureDictionary = {'class': 'public class {0} \n{{\n', 'field': '\tprivate {0} {1};\n\n', 'end': '}', 'suffix': '.java'}
		annotationDictionary = {'class': '@Entity\n@Table(name = "{0}")\n', 'field': '\t@Column(name = "{0}")\n'}

		return createBody(a_csv_string, fieldType, positionDictionary, structureDictionary, annotationDictionary = annotationDictionary)

	def createDtoFile(self, a_csv_string):
		fieldType = {'L': 'BigDecimal', 'N': 'Long', 'D': 'Date', 'A': 'String'}
		positionDictionary = {'class': self.col_with_tab_name, 'type': self.col_with_flag,
							  'field': self.col_with_name}
		structureDictionary = {'class': 'public class {0}DTO \n{{\n', 'field': '\tprivate {0} {1};\n\n', 'end': '}',
							   'suffix': '.dto'}

		return createBody(a_csv_string, fieldType, positionDictionary, structureDictionary)

	def createSql(self, a_csv_string):
		body = ''

		isOpen = False

		buff = StringIO(a_csv_string)

		content = csv.reader(buff, delimiter=";")


		sqlContent = ''
		commentContent = ''

		isOpen = False

		outputFile = None

		tableName = ''
		for row in content:
			col_with_name = row[self.col_with_tab_name].strip()
			fieldsType = row[self.col_with_flag].strip()
			field = row[self.col_with_name].strip()
			length = row[self.col_with_length].strip()

			if self.col_with_comment is not None:
				comment = row[self.col_with_comment].strip()

			if col_with_name != '':
				tableName = col_with_name
				if isOpen:
					sqlContent = sqlContent[:-2]
					sqlContent += '\n);'
					isOpen = False

				isOpen = True

				sqlContent += '\n\n'

				sqlContent += 'CREATE TABLE {0}'.format(col_with_name) + '\n(\n'
				commentContent = ''
			if isOpen == True and self.col_with_name != '' and row[self.col_with_flag] != '':
				sqlContent += createField(fieldsType, field, length)

			# if self.col_with_comment is not None:
			# 	commentContent += self.createComment(tableName, field, comment)

		sqlContent = sqlContent[:-2]
		sqlContent += '\n);'

		return sqlContent

