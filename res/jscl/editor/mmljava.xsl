<?xml version='1.0' encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:m="http://www.w3.org/1998/Math/MathML"
		version='1.0'>

<xsl:output method="text" indent="no" encoding="UTF-8"/>

<xsl:strip-space elements="m:*"/>

<xsl:template match="m:math">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="m:cn">
	<xsl:text>JSCLInteger.valueOf(&#x00022;</xsl:text>
	<xsl:apply-templates/>
	<xsl:text>&#x00022;)</xsl:text>
</xsl:template>

<xsl:template match="m:cn[@type='integer' and @base!=10]">
	<xsl:text>ModularInteger.valueOf(</xsl:text>
	<xsl:apply-templates/>
	<xsl:text>, </xsl:text>
	<xsl:value-of select="@base"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:cn[@type='rational']">
	<xsl:text>Rational.valueOf(&#x00022;</xsl:text>
	<xsl:apply-templates select="text()[1]"/>
	<xsl:text>&#x00022;, &#x00022;</xsl:text>
	<xsl:apply-templates select="text()[2]"/>
	<xsl:text>&#x00022;)</xsl:text>
</xsl:template>

<xsl:template match="m:cn[@type='real']">
	<xsl:text>JSCLDouble.valueOf(</xsl:text>
	<xsl:apply-templates/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:cn[@type='complex']">
	<xsl:text>Complex.valueOf(</xsl:text>
	<xsl:apply-templates select="text()[1]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="text()[2]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:exponentiale">
	<xsl:text>exp(JSCLInteger.valueOf(1))</xsl:text>
</xsl:template>

<xsl:template match="m:imaginaryi">
	<xsl:text>sqrt(JSCLInteger.valueOf(-1))</xsl:text>
</xsl:template>

<xsl:template match="m:pi">
	<xsl:text>pi</xsl:text>
</xsl:template>

<xsl:template match="m:infinity">
	<xsl:text>oo</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:root]]">
	<xsl:choose>
		<xsl:when test="m:degree">
			<xsl:choose>
				<xsl:when test="m:degree/m:cn/text()='3'">
					<xsl:text>cubic(</xsl:text>
					<xsl:apply-templates select="*[3]"/>
					<xsl:text>)</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>sqrt(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>)</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="m:apply[*[1][
 self::m:sin or
 self::m:cos or
 self::m:tan or
 self::m:cot or
 self::m:arcsin or
 self::m:arccos or
 self::m:arctan or
 self::m:arccot or
 self::m:sinh or
 self::m:cosh or
 self::m:tanh or
 self::m:coth or
 self::m:arcsinh or
 self::m:arccosh or
 self::m:arctanh or
 self::m:arccoth or
 self::m:exp or
 self::m:ln]]">
	<xsl:value-of select="local-name(*[1])"/>
	<xsl:text>(</xsl:text>
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:ci]]">
	<xsl:choose>
		<xsl:when test="*[1]/*[1]/*[1]/text() = 'root'">
			<xsl:apply-templates select="*[1]/*[1]/*[1]"/>
			<xsl:text>(new Generic[] {</xsl:text>
			<xsl:for-each select="*[position() &gt; 1]">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>}, </xsl:text>
			<xsl:apply-templates select="*[1]/*[1]/*[2]"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:for-each select="*[position() &gt; 1]">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>)</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="m:ci | m:mi">
	<xsl:choose>
		<xsl:when test="text() = '&#x003B1;'"><xsl:text>alpha</xsl:text></xsl:when>
		<xsl:otherwise><xsl:apply-templates/></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="m:ci[*[1][self::m:msub[*[1][self::m:mi] and *[2][self::m:mrow]]]]">
	<xsl:apply-templates select="*[1]/*[1]"/>
	<xsl:for-each select="*[1]/*[2]/*">
		<xsl:text>[</xsl:text>
		<xsl:apply-templates select="text()"/>
		<xsl:text>]</xsl:text>
	</xsl:for-each>
</xsl:template>

<xsl:template match="m:ci[*[1][self::m:msub[*[1][self::m:mrow[*[1][self::m:mi]]] and *[2][self::m:mrow]]]]">
	<xsl:apply-templates select="*[1]/*[1]/*[1]"/>
	<xsl:for-each select="*[1]/*[1]/*[position() &gt; 1]">
		<xsl:text>_</xsl:text>
	</xsl:for-each>
	<xsl:for-each select="*[1]/*[2]/*">
		<xsl:text>[</xsl:text>
		<xsl:apply-templates select="text()"/>
		<xsl:text>]</xsl:text>
	</xsl:for-each>
</xsl:template>

<xsl:template match="m:ci[*[1][self::m:mrow[*[1][self::m:mi]]]]">
	<xsl:apply-templates select="*[1]/*[1]"/>
	<xsl:for-each select="*[1]/*[position() &gt; 1]">
		<xsl:text>_</xsl:text>
	</xsl:for-each>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:minus] and count(*) = 2]">
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>.negate()</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:minus] and count(*) &gt; 2]">
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>.subtract(</xsl:text>
	<xsl:apply-templates select="*[3]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:plus]]">
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>.add(</xsl:text>
	<xsl:apply-templates select="*[3]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:times]]">
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>.multiply(</xsl:text>
	<xsl:apply-templates select="*[3]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:divide]]">
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>.divide(</xsl:text>
	<xsl:apply-templates select="*[3]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:power]]">
	<xsl:choose>
		<xsl:when test="*[3][self::m:cn[not(@type) or @type='integer']]">
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>.pow(</xsl:text>
			<xsl:apply-templates select="*[3]/text()"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>power(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[3]"/>
			<xsl:text>)</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="m:vector">
	<xsl:text>new JSCLVector(new Generic[] {</xsl:text>
	<xsl:for-each select="*">
		<xsl:apply-templates select="."/>
		<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
	</xsl:for-each>
	<xsl:text>})</xsl:text>
</xsl:template>

<xsl:template match="m:matrix">
	<xsl:text>new Matrix(new Generic[][] {</xsl:text>
	<xsl:apply-templates/>
	<xsl:text>})</xsl:text>
</xsl:template>

<xsl:template match="m:matrixrow">
	<xsl:text>{</xsl:text>
	<xsl:for-each select="*">
		<xsl:apply-templates select="."/>
		<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
	</xsl:for-each>
	<xsl:text>}</xsl:text>
	<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
</xsl:template>

</xsl:stylesheet>