<?xml version='1.0' encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:m="http://www.w3.org/1998/Math/MathML"
		xmlns:x="http://www.w3.org/1999/xhtml"
		xmlns:s="http://www.w3.org/2000/svg"
		version='1.0'>

<xsl:output method="text" indent="no" encoding="UTF-8"/>

<xsl:strip-space elements="m:*"/>

<xsl:template match="x:a">
	<xsl:choose>
		<xsl:when test="starts-with(@href,'mvn')"><xsl:value-of select="text()"/></xsl:when>
		<xsl:when test="starts-with(@href,'mailto')"><xsl:value-of select="substring-after(@href, ':')"/></xsl:when>
		<xsl:otherwise><xsl:value-of select="@href"/></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="s:svg"/>

<xsl:template match="m:math">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="m:cn">
	<xsl:call-template name="integer">
		<xsl:with-param name="value" select="text()"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="m:cn[@type='integer' and @base!=10]">
	<xsl:text>modint(</xsl:text>
	<xsl:apply-templates/>
	<xsl:text>, </xsl:text>
	<xsl:value-of select="@base"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:cn[@type='rational']">
	<xsl:text>rational(</xsl:text>
	<xsl:call-template name="integer">
		<xsl:with-param name="value" select="text()[1]"/>
		<xsl:with-param name="p" select="0"/>
	</xsl:call-template>
	<xsl:text>, </xsl:text>
	<xsl:call-template name="integer">
		<xsl:with-param name="value" select="text()[2]"/>
		<xsl:with-param name="p" select="0"/>
	</xsl:call-template>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:cn[@type='real']">
	<xsl:text>real(</xsl:text>
	<xsl:apply-templates/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:cn[@type='complex-cartesian']">
	<xsl:text>complex(</xsl:text>
	<xsl:apply-templates select="text()[1]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="text()[2]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:true | m:false">
	<xsl:text>bool(</xsl:text>
	<xsl:value-of select="local-name(*[1])"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:exponentiale">
	<xsl:text>exp(</xsl:text>
	<xsl:call-template name="integer">
		<xsl:with-param name="value">1</xsl:with-param>
	</xsl:call-template>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:imaginaryi">
	<xsl:text>sqrt(</xsl:text>
	<xsl:call-template name="integer">
		<xsl:with-param name="value">-1</xsl:with-param>
	</xsl:call-template>
	<xsl:text>)</xsl:text>
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
 self::m:ln or
 self::m:abs or
 self::m:conjugate]]">
	<xsl:value-of select="local-name(*[1])"/>
	<xsl:text>(</xsl:text>
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:diff] and *[2][self::m:bvar[*[1][self::m:degree]]]]">
	<xsl:text>d(</xsl:text>
	<xsl:apply-templates select="*[3]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="*[2]/*[2]"/>
	<xsl:if test="not(*[2]/*[1]/m:cn) or *[2]/*[1]/m:cn/text() != '1'">
		<xsl:text>, </xsl:text>
		<xsl:apply-templates select="*[2]/*[2]"/>
		<xsl:text>, </xsl:text>
		<xsl:apply-templates select="*[2]/*[1]"/>
	</xsl:if>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:apply[*[1][self::m:diff] and *[2][self::m:bvar[*[1][self::m:degree]]]]]]">
	<xsl:text>d(</xsl:text>
	<xsl:apply-templates select="*[1]/*[3]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="*[1]/*[2]/*[2]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="*[2]"/>
	<xsl:if test="not(*[1]/*[2]/*[1]/m:cn) or *[1]/*[2]/*[1]/m:cn/text() != '1'">
		<xsl:text>, </xsl:text>
		<xsl:apply-templates select="*[1]/*[2]/*[1]"/>
	</xsl:if>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:int]]">
	<xsl:text>integral(</xsl:text>
	<xsl:choose>
		<xsl:when test="*[2][self::m:lowlimit] and *[3][self::m:uplimit] and *[4][self::m:bvar]">
			<xsl:apply-templates select="*[5]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[4]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[3]"/>
		</xsl:when>
		<xsl:when test="*[2][self::m:bvar]">
			<xsl:apply-templates select="*[3]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[2]"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates/>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:sum or self::m:product]]">
	<xsl:value-of select="local-name(*[1])"/>
	<xsl:text>(</xsl:text>
	<xsl:apply-templates select="*[5]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="*[4]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="*[3]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:factorial or self::m:transpose or self::m:determinant]]">
	<xsl:value-of select="local-name(*[1])"/>
	<xsl:text>(</xsl:text>
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:quotient or self::m:rem]]">
	<xsl:value-of select="local-name(*[1])"/>
	<xsl:text>(</xsl:text>
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="*[3]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:limit]]">
	<xsl:value-of select="local-name(*[1])"/>
	<xsl:text>(</xsl:text>
	<xsl:apply-templates select="*[4]"/>
	<xsl:text>, </xsl:text>
	<xsl:apply-templates select="*[3]"/>
	<xsl:text>, </xsl:text>
	<xsl:choose>
		<xsl:when test="*[2]/m:msup">
			<xsl:apply-templates select="*[2]/*[1]/*[1]"/>
			<xsl:text>, </xsl:text>
			<xsl:choose>
				<xsl:when test="*[2]/*[1]/*[2]/text() = '-'">
					<xsl:call-template name="integer">
						<xsl:with-param name="value">-1</xsl:with-param>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="integer">
						<xsl:with-param name="value">1</xsl:with-param>
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="*[2]"/>
		</xsl:otherwise>
	</xsl:choose>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:grad or self::m:divergence or self::m:curl or self::m:laplacian]]">
	<xsl:value-of select="local-name(*[1])"/>
	<xsl:text>(</xsl:text>
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>, new Generic[] {</xsl:text>
	<xsl:for-each select="*[3]/*">
		<xsl:apply-templates select="."/>
		<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
	</xsl:for-each>
	<xsl:text>})</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:apply[*[1][self::m:transpose]]]]">
	<xsl:choose>
		<xsl:when test="*[1]/*[1]/*[1]/*[1]/*[1]/text() = '&#x02207;'">
			<xsl:text>jacobian(</xsl:text>
			<xsl:apply-templates select="*[2]/*[1]/*[1]"/>
			<xsl:text>, new Generic[] {</xsl:text>
			<xsl:for-each select="*[1]/*[1]/*[1]/*[1]/*[2]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>})</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates/>
		</xsl:otherwise>
	</xsl:choose>
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
			<xsl:text>})</xsl:text>
			<xsl:apply-templates select="*[1]/*[1]/*[2]"/>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'elementary' or
				*[1]/text() = 'factorize' or
				*[1]/text() = 'simplify' or
				*[1]/text() = 'numeric' or
				*[1]/text() = 'quote' or
				*[1]/text() = 'trace'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/*[1]/*[1]/text() = '&#x025A1;'">
			<xsl:text>dalembertian(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, new Generic[] {</xsl:text>
			<xsl:for-each select="*[1]/*[1]/*[2]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>})</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/*[1]/*[1]/text() = '&#x02207;'">
			<xsl:text>del(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, new Generic[] {</xsl:text>
			<xsl:for-each select="*[1]/*[1]/*[2]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>}</xsl:text>
			<xsl:if test="*[3]">
				<xsl:text>, </xsl:text>
				<xsl:apply-templates select="*[3]"/>
			</xsl:if>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'cl'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[3]"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'groebner'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, new Generic[] {</xsl:text>
			<xsl:for-each select="*[3]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>}</xsl:text>
			<xsl:if test="*[4]">
				<xsl:text>, </xsl:text>
				<xsl:apply-templates select="*[4]"/>
			</xsl:if>
			<xsl:if test="*[5]">
				<xsl:text>, </xsl:text>
				<xsl:apply-templates select="*[5]"/>
			</xsl:if>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'elim'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>.apply(new Generic[] {</xsl:text>
			<xsl:for-each select="*[position() &gt; 1]">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>})</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="m:ci | m:mi">
	<xsl:choose>
		<xsl:when test="text() = '&#x003B1;'"><xsl:text>alpha</xsl:text></xsl:when>
		<xsl:otherwise><xsl:apply-templates/></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="m:mo">
	<xsl:value-of select="translate(text(),'&#x02032;','_')"/>
</xsl:template>

<xsl:template match="m:mrow">
	<xsl:choose>
		<xsl:when test="*[1][self::m:mi] and *[2][self::m:mo]">
			<xsl:apply-templates/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:for-each select="*">
				<xsl:text>[</xsl:text>
				<xsl:apply-templates/>
				<xsl:text>]</xsl:text>
			</xsl:for-each>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="m:mfenced">
	<xsl:for-each select="*">
		<xsl:text>_</xsl:text>
		<xsl:apply-templates select="text()"/>
	</xsl:for-each>
</xsl:template>

<xsl:template match="m:ci[*[1][self::m:msubsup[*[1][self::m:mi] and *[2][self::m:mrow] and *[3][self::m:mfenced]]]]">
	<xsl:apply-templates select="*[1]/*[1]"/>
	<xsl:apply-templates select="*[1]/*[3]"/>
	<xsl:apply-templates select="*[1]/*[2]"/>
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
	<xsl:text>vector(new Generic[] {</xsl:text>
	<xsl:for-each select="*">
		<xsl:apply-templates select="."/>
		<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
	</xsl:for-each>
	<xsl:text>})</xsl:text>
</xsl:template>

<xsl:template match="m:matrix">
	<xsl:text>matrix(new Generic[][] {</xsl:text>
	<xsl:for-each select="*">
		<xsl:apply-templates select="."/>
		<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
	</xsl:for-each>
	<xsl:text>})</xsl:text>
</xsl:template>

<xsl:template match="m:matrixrow">
	<xsl:text>{</xsl:text>
	<xsl:for-each select="*">
		<xsl:apply-templates select="."/>
		<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
	</xsl:for-each>
	<xsl:text>}</xsl:text>
</xsl:template>

<xsl:template name="integer">
	<xsl:param name="value"/>
	<xsl:param name="p" select="1"/>
	<xsl:if test="0 &lt; $p"><xsl:text>integer(</xsl:text></xsl:if>
	<xsl:text>&#x00022;</xsl:text>
	<xsl:value-of select="$value"/>
	<xsl:text>&#x00022;</xsl:text>
	<xsl:if test="0 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

</xsl:stylesheet>