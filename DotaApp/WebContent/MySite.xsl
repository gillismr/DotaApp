<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" indent="yes"/>
	<xsl:template match="/">
		<html>
		<head>
		</head>
		<body>
			<h1>Hero</h1>
			<ol>
			<xsl:for-each select="Hero">
				<li>
					<xsl:value-of select="@name"/>
					
				</li>
			</xsl:for-each>	
			</ol>
		</body>
		</html>
	</xsl:template>
</xsl:stylesheet>