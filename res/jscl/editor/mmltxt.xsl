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

<xsl:template match="m:true">
	<xsl:text>true</xsl:text>
</xsl:template>

<xsl:template match="m:false">
	<xsl:text>false</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:and]]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="1 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="1"/>
	</xsl:apply-templates>
	<xsl:text>&amp;</xsl:text>
	<xsl:apply-templates select="*[3]">
	    	<xsl:with-param name="p" select="1"/>
	</xsl:apply-templates>
	<xsl:if test="1 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:or]]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="0 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="0"/>
	</xsl:apply-templates>
	<xsl:text>|</xsl:text>
	<xsl:apply-templates select="*[3]">
	    	<xsl:with-param name="p" select="0"/>
	</xsl:apply-templates>
	<xsl:if test="0 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:xor]]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="0 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="0"/>
	</xsl:apply-templates>
	<xsl:text>^</xsl:text>
	<xsl:apply-templates select="*[3]">
	    	<xsl:with-param name="p" select="0"/>
	</xsl:apply-templates>
	<xsl:if test="0 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:not]]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="2 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:text>!</xsl:text>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="2"/>
	</xsl:apply-templates>
	<xsl:if test="2 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:implies]]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="2 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="2"/>
	</xsl:apply-templates>
	<xsl:text>=&gt;</xsl:text>
	<xsl:apply-templates select="*[3]">
	    	<xsl:with-param name="p" select="2"/>
	</xsl:apply-templates>
	<xsl:if test="2 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:exponentiale">
	<xsl:text>exp(1)</xsl:text>
</xsl:template>

<xsl:template match="m:imaginaryi">
	<xsl:text>sqrt(-1)</xsl:text>
</xsl:template>

<xsl:template match="m:pi">
	<xsl:text>pi</xsl:text>
</xsl:template>

<xsl:template match="m:infinity">
	<xsl:text>oo</xsl:text>
</xsl:template>

<xsl:template match="m:cn">
	<xsl:param name="p" select="-1"/>
	<xsl:variable name="content">
		<xsl:value-of select="number(text())"/>
	</xsl:variable>
	<xsl:if test="-1 &lt; $p and $content &lt; 0"><xsl:text>(</xsl:text></xsl:if>
	<xsl:value-of select="text()"/>
	<xsl:if test="-1 &lt; $p and $content &lt; 0"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:cn[@type='rational']">
	<xsl:param name="p" select="-1"/>
	<xsl:variable name="numerator">
		<xsl:value-of select="number(text()[1])"/>
	</xsl:variable>
	<xsl:if test="1 &lt; $p or (-1 &lt; $p and $numerator &lt; 0)"><xsl:text>(</xsl:text></xsl:if>
	<xsl:value-of select="text()[1]"/>
	<xsl:text>/</xsl:text>
	<xsl:value-of select="text()[2]"/>
	<xsl:if test="1 &lt; $p or (-1 &lt; $p and $numerator &lt; 0)"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:cn[@type='complex-cartesian']">
	<xsl:param name="p" select="-1"/>
	<xsl:variable name="real">
		<xsl:value-of select="number(text()[1])"/>
	</xsl:variable>
	<xsl:variable name="imag">
		<xsl:value-of select="number(text()[2])"/>
	</xsl:variable>
	<xsl:choose>
		<xsl:when test="$imag = 0">
			<xsl:value-of select="$real"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:choose>
				<xsl:when test="$real = 0">
					<xsl:choose>
						<xsl:when test="$imag = 1">
							<xsl:text>sqrt(-1)</xsl:text>
						</xsl:when>
						<xsl:when test="$imag = -1">
							<xsl:if test="-1 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
							<xsl:text>-sqrt(-1)</xsl:text>
							<xsl:if test="-1 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<xsl:if test="1 &lt; $p or (-1 &lt; $p and $imag &lt; 0)"><xsl:text>(</xsl:text></xsl:if>
							<xsl:value-of select="text()[2]"/>
							<xsl:text>*sqrt(-1)</xsl:text>
							<xsl:if test="1 &lt; $p or (-1 &lt; $p and $imag &lt; 0)"><xsl:text>)</xsl:text></xsl:if>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:if test="0 &lt; $p or (-1 &lt; $p and $real &lt; 0)"><xsl:text>(</xsl:text></xsl:if>
					<xsl:value-of select="text()[1]"/>
					<xsl:choose>
						<xsl:when test="$imag = 1">
							<xsl:text>+sqrt(-1)</xsl:text>
						</xsl:when>
						<xsl:when test="$imag = -1">
							<xsl:text>-sqrt(-1)</xsl:text>
						</xsl:when>
						<xsl:when test="$imag &lt; 0">
							<xsl:value-of select="text()[2]"/>
							<xsl:text>*sqrt(-1)</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>+</xsl:text>
							<xsl:value-of select="text()[2]"/>
							<xsl:text>*sqrt(-1)</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:if test="0 &lt; $p or (-1 &lt; $p and $real &lt; 0)"><xsl:text>)</xsl:text></xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="m:ci | m:mi">
	<xsl:variable name="n">
		<xsl:choose>
			<xsl:when test="contains(text(), '&#x02032;')">
				<xsl:value-of select="string-length(substring-before(text(), '&#x02032;'))+1"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="string-length(text())+1"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:call-template name="greek">
		<xsl:with-param name="value" select="substring(text(), 0, $n)"/>
	</xsl:call-template>
	<xsl:variable name="vApos">'</xsl:variable>
	<xsl:value-of select="translate(substring(text(), $n), '&#x02032;', $vApos)"/>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:root]]">
	<xsl:choose>
		<xsl:when test="m:degree">
			<xsl:if test="m:degree/m:cn/text()='3'">
				<xsl:text>cubic(</xsl:text>
				<xsl:apply-templates select="*[3]"/>
				<xsl:text>)</xsl:text>
			</xsl:if>
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
	<xsl:choose>
		<xsl:when test="local-name(*[1])='ln'">
			<xsl:text>log</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="local-name(*[1])"/>
		</xsl:otherwise>
	</xsl:choose>
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

<xsl:template match="m:apply[*[1][self::m:transpose or self::m:determinant]]">
	<xsl:value-of select="local-name(*[1])"/>
	<xsl:text>(</xsl:text>
	<xsl:apply-templates select="*[2]"/>
	<xsl:text>)</xsl:text>
</xsl:template>

<xsl:template match="m:apply[*[1][
self::m:quotient or
self::m:rem or
self::m:eq or
self::m:neq or
self::m:leq or
self::m:lt or
self::m:geq or
self::m:gt or
self::m:approx or
self::m:factorof]]">
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
					<xsl:text>-1</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>1</xsl:text>
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
	<xsl:text>, {</xsl:text>
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
			<xsl:text>, {</xsl:text>
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

<xsl:template match="m:apply[*[1][self::m:mo]]">
	<xsl:choose>
		<xsl:when test="*[1]/text() = '&#x02227;'">
			<xsl:text>vector({</xsl:text>
			<xsl:for-each select="*[2]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>}, {</xsl:text>
			<xsl:for-each select="*[3]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>})</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = '&#x02A2F;'">
			<xsl:text>tensor({</xsl:text>
			<xsl:for-each select="*[2]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>}, {</xsl:text>
			<xsl:for-each select="*[3]/*">
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
		<xsl:when test="*[1]/text() = 'expand' or
				*[1]/text() = 'factorize' or
				*[1]/text() = 'elementary' or
				*[1]/text() = 'simplify' or
				*[1]/text() = 'numeric' or
				*[1]/text() = 'quote' or
				*[1]/text() = 'trace' or
				*[1]/text() = 'eulerphi' or
				*[1]/text() = 'primitiveroots'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'coef' or
				*[1]/text() = 'mod' or
				*[1]/text() = 'modinv' or
				*[1]/text() = 'graph'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[3]"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'modpow'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[3]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[4]"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'taylor'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[3]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[4]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[5]"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'matrix'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>({</xsl:text>
			<xsl:for-each select="*[2]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>}, {</xsl:text>
			<xsl:for-each select="*[3]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>})</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'complex' or
				*[1]/text() = 'quaternion'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>({</xsl:text>
			<xsl:for-each select="*[2]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>}, {</xsl:text>
			<xsl:for-each select="*[3]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>})</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'geometric'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>({</xsl:text>
			<xsl:for-each select="*[2]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>}, {</xsl:text>
			<xsl:for-each select="*[3]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>}</xsl:text>
			<xsl:if test="*[4]">
				<xsl:text>, </xsl:text>
				<xsl:apply-templates select="*[4]"/>
			</xsl:if>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'subst'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, </xsl:text>
			<xsl:choose>
				<xsl:when test="*[3][self::m:vector]">
					<xsl:text>{</xsl:text>
					<xsl:for-each select="*[3]/*">
						<xsl:apply-templates select="."/>
						<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
					</xsl:for-each>
					<xsl:text>}, {</xsl:text>
					<xsl:for-each select="*[4]/*">
						<xsl:apply-templates select="."/>
						<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
					</xsl:for-each>
					<xsl:text>}</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="*[3]"/>
					<xsl:text>, </xsl:text>
					<xsl:apply-templates select="*[4]"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'solve'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[3]"/>
			<xsl:if test="*[4]">
				<xsl:text>, </xsl:text>
				<xsl:apply-templates select="*[4]/text()"/>
			</xsl:if>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/*[1]/*[1]/text() = '&#x025A1;'">
			<xsl:text>dalembertian(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, {</xsl:text>
			<xsl:for-each select="*[1]/*[1]/*[2]/*">
				<xsl:apply-templates select="."/>
				<xsl:if test="position()!=last()"><xsl:text>, </xsl:text></xsl:if>
			</xsl:for-each>
			<xsl:text>})</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/*[1]/*[1]/text() = '&#x02207;'">
			<xsl:text>del(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, {</xsl:text>
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
			<xsl:apply-templates select="*[2]/text()"/>
			<xsl:text>, </xsl:text>
			<xsl:apply-templates select="*[3]/text()"/>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'groebner'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]"/>
			<xsl:text>, {</xsl:text>
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
				<xsl:apply-templates select="*[5]/text()"/>
			</xsl:if>
			<xsl:text>)</xsl:text>
		</xsl:when>
		<xsl:when test="*[1]/text() = 'elim'">
			<xsl:apply-templates select="*[1]"/>
			<xsl:text>(</xsl:text>
			<xsl:apply-templates select="*[2]/text()"/>
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

<xsl:template match="m:msubsup[*[1][self::m:mi]]">
	<xsl:if test="*[1]/text() = 'C'">
		<xsl:apply-templates select="*[1]"/>
		<xsl:text>(</xsl:text>
		<xsl:apply-templates select="*[2]"/>
		<xsl:text>, </xsl:text>
		<xsl:apply-templates select="*[3]"/>
		<xsl:text>)</xsl:text>
	</xsl:if>
</xsl:template>

<xsl:template match="m:mrow">
	<xsl:for-each select="*">
		<xsl:text>[</xsl:text>
		<xsl:apply-templates select="."/>
		<xsl:text>]</xsl:text>
	</xsl:for-each>
</xsl:template>

<xsl:template match="m:ci[*[1][self::m:msub[*[1][self::m:mi] and *[2][self::m:mrow]]]]">
	<xsl:apply-templates select="*[1]/*[1]"/>
	<xsl:apply-templates select="*[1]/*[2]"/>
</xsl:template>

<xsl:template match="m:ci[*[1][self::m:msubsup[*[1][self::m:mi] and *[2][self::m:mrow] and *[3][self::m:mfenced]]]]">
	<xsl:apply-templates select="*[1]/*[1]"/>
	<xsl:apply-templates select="*[1]/*[3]"/>
	<xsl:apply-templates select="*[1]/*[2]"/>
</xsl:template>

<xsl:template match="m:ci[*[1][self::m:msup[*[1][self::m:mi] and *[2][self::m:mfenced]]]]">
	<xsl:apply-templates select="*[1]/*[1]"/>
	<xsl:apply-templates select="*[1]/*[2]"/>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:minus] and count(*) = 2]">
	<xsl:param name="p" select="-1"/>
	<xsl:if test="-1 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:text>-</xsl:text>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="1"/>
	</xsl:apply-templates>
	<xsl:if test="-1 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:minus] and count(*) &gt; 2]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="0 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="-1"/>
	</xsl:apply-templates>
	<xsl:text>-</xsl:text>
	<xsl:apply-templates select="*[3]">
		<xsl:with-param name="p" select="1"/>
	</xsl:apply-templates>
	<xsl:if test="0 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:plus]]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="0 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="-1"/>
	</xsl:apply-templates>
	<xsl:text>+</xsl:text>
	<xsl:apply-templates select="*[3]">
		<xsl:with-param name="p" select="0"/>
	</xsl:apply-templates>
	<xsl:if test="0 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:times]]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="1 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="1"/>
	</xsl:apply-templates>
	<xsl:text>*</xsl:text>
	<xsl:apply-templates select="*[3]">
		<xsl:with-param name="p" select="1"/>
	</xsl:apply-templates>
	<xsl:if test="1 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:divide]]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="1 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="2"/>
	</xsl:apply-templates>
	<xsl:text>/</xsl:text>
	<xsl:apply-templates select="*[3]">
		<xsl:with-param name="p" select="2"/>
	</xsl:apply-templates>
	<xsl:if test="1 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:power]]">
	<xsl:param name="p" select="0"/>
	<xsl:if test="2 &lt; $p"><xsl:text>(</xsl:text></xsl:if>
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="2"/>
	</xsl:apply-templates>
	<xsl:text>^</xsl:text>
	<xsl:apply-templates select="*[3]">
		<xsl:with-param name="p" select="2"/>
	</xsl:apply-templates>
	<xsl:if test="2 &lt; $p"><xsl:text>)</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="m:apply[*[1][self::m:factorial]]">
	<xsl:apply-templates select="*[2]">
		<xsl:with-param name="p" select="3"/>
	</xsl:apply-templates>
	<xsl:call-template name="factorials">
		<xsl:with-param name="count">
			<xsl:choose>
				<xsl:when test="*[3]">
					<xsl:value-of select="*[3]/text()"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="1"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:with-param>
	</xsl:call-template> 
</xsl:template>

<xsl:template name="factorials">
	<xsl:param name="count"/>
	<xsl:if test="$count &gt; 0">
		<xsl:text>!</xsl:text>
		<xsl:call-template name="factorials">
			<xsl:with-param name="count">
				<xsl:value-of select="$count - 1"/>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:if>
</xsl:template>

<xsl:template match="m:vector | m:matrix | m:matrixrow | m:mfenced">
	<xsl:text>{</xsl:text>
	<xsl:for-each select="*">
		<xsl:apply-templates select="."/>
		<xsl:if test="position() &lt; last()"><xsl:text>, </xsl:text></xsl:if>
	</xsl:for-each>
	<xsl:text>}</xsl:text>
</xsl:template>

<xsl:template name="greek">
	<xsl:param name="value"/>
	<xsl:choose>
		<xsl:when test="$value = '&#x00391;'"><xsl:text>Alpha</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x00392;'"><xsl:text>Beta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x00393;'"><xsl:text>Gamma</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x00394;'"><xsl:text>Delta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x00395;'"><xsl:text>Epsilon</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x00396;'"><xsl:text>Zeta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x00397;'"><xsl:text>Eta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x00398;'"><xsl:text>Theta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x00399;'"><xsl:text>Iota</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x0039A;'"><xsl:text>Kappa</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x0039B;'"><xsl:text>Lambda</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x0039C;'"><xsl:text>Mu</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x0039D;'"><xsl:text>Nu</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x0039E;'"><xsl:text>Xi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003A0;'"><xsl:text>Pi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003A1;'"><xsl:text>Rho</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003A3;'"><xsl:text>Sigma</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003A4;'"><xsl:text>Tau</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003A5;'"><xsl:text>Upsilon</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003A6;'"><xsl:text>Phi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003A7;'"><xsl:text>Chi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003A8;'"><xsl:text>Psi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003A9;'"><xsl:text>Omega</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003B1;'"><xsl:text>alpha</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003B2;'"><xsl:text>beta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003B3;'"><xsl:text>gamma</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003B4;'"><xsl:text>delta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003B5;'"><xsl:text>epsilon</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003B6;'"><xsl:text>zeta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003B7;'"><xsl:text>eta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003B8;'"><xsl:text>theta</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003B9;'"><xsl:text>iota</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003BA;'"><xsl:text>kappa</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003BB;'"><xsl:text>lambda</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003BC;'"><xsl:text>mu</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003BD;'"><xsl:text>nu</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003BE;'"><xsl:text>xi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003C0;'"><xsl:text>pi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003C1;'"><xsl:text>rho</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003C3;'"><xsl:text>sigma</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003C4;'"><xsl:text>tau</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003C5;'"><xsl:text>upsilon</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003C6;'"><xsl:text>phi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003C7;'"><xsl:text>chi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003C8;'"><xsl:text>psi</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x003C9;'"><xsl:text>omega</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x02207;'"><xsl:text>nabla</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x02135;'"><xsl:text>aleph</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x0210F;'"><xsl:text>hbar</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x0210B;'"><xsl:text>hamilt</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x02112;'"><xsl:text>lagran</xsl:text></xsl:when>
		<xsl:when test="$value = '&#x025A1;'"><xsl:text>square</xsl:text></xsl:when>
		<xsl:otherwise><xsl:value-of select="$value"/></xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>