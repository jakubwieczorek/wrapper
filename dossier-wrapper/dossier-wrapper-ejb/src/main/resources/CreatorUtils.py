import sys, csv
from StringIO import StringIO

def createBody(a_csv_string, aFieldDictionary, aPositionDictionary, aStructureDictionary, **kwargs):
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

def createField(aType, aField, aLength):

    fieldType = {'L': 'NUMBER({0}, {1})', 'N': 'NUMBER({0})', 'D': 'DATE', 'A': 'VARCHAR2({0})'}

    if fieldType[aType] == 'L':
        [total, decimal] = aLength.split()
        return '\t{0} {1},\n'.format(aField, fieldType(aType).format(total, decimal))
    else:
        return '\t{0} {1},\n'.format(aField, fieldType[aType].format(aLength))