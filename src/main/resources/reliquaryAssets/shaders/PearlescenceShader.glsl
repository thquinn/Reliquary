#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float x_time;

vec4 rgba() {
    return v_color * texture2D(u_texture, v_texCoords);
}
vec2 map(vec2 value, vec2 inMin, vec2 inMax, vec2 outMin, vec2 outMax) {
  // from https://github.com/msfeldstein/glsl-map/blob/master/index.glsl
  return outMin + (outMax - outMin) * (value - inMin) / (inMax - inMin);
}
float smin(float a, float b, float k) {
    float h = clamp(0.5 + 0.5*(a-b)/k, 0.0, 1.0);
    return mix(a, b, h) - k*h*(1.0-h);
}

const mat2 m = mat2( 0.80,  0.60, -0.60,  0.80 );
float hash( vec2 p )
{
	float h = dot(p,vec2(127.1,311.7));
    return -1.0 + 2.0*fract(fract(h)*43758.5453123);
}
float noise( in vec2 p )
{
    vec2 i = floor( p );
    vec2 f = fract( p );
	
	vec2 u = f*f*(3.0-2.0*f);

    return mix( mix( hash( i + vec2(0.0,0.0) ), 
                     hash( i + vec2(1.0,0.0) ), u.x),
                mix( hash( i + vec2(0.0,1.0) ), 
                     hash( i + vec2(1.0,1.0) ), u.x), u.y);
}
float fbm( vec2 p )
{
    float f = 0.0;
    f += 0.5000*noise( p ); p = m*p*2.02;
    f += 0.2500*noise( p ); p = m*p*2.03;
    f += 0.1250*noise( p ); p = m*p*2.01;
    f += 0.0625*noise( p );
    return f/0.9375;
}
vec2 fbm2( in vec2 p )
{
    return vec2( fbm(p.xy), fbm(p.yx) );
}

void main() {
    vec4 outputColor = rgba();
	vec3 white = vec3(1., 1., 1.);
    vec3 pink = vec3(.75, .666, .6);
    vec3 green = vec3(.74, .75, .71);
	
	// map to the 512/bg_skill_gray cardui atlas region
	vec2 uv = map(v_texCoords, vec2(.45, .255), vec2(.62, .47), vec2(-1, 1), vec2(1, -1));
	
    vec3 color = mix(green, pink, (uv.y + 1.) / 2.);
    uv.y *= 1.66;
    vec2 specularUV = uv;
    specularUV.y /= 1. - abs(uv.x) * .1;
    vec2 specularPosition = vec2(0, .85 + .02 * sin(x_time * .5));
    vec2 topSpecularDelta = specularPosition - vec2(specularUV.x, specularUV.y);
    vec2 bottomSpecularDelta = specularPosition - vec2(specularUV.x, -specularUV.y);
    topSpecularDelta.x *= .7;
    bottomSpecularDelta.x *= .7;
    float topSpecularDistance = length(topSpecularDelta);
    float bottomSpecularDistance = length(bottomSpecularDelta);
    float specularDistance = smin(topSpecularDistance, bottomSpecularDistance, 2.);
    float specular = max(0., 1. - specularDistance);
    float osc = length(uv + vec2(.7 * cos(x_time * .243), 1. * sin(x_time * .5)));
    osc = mix(osc, 1., .7);
    specular = min(1., specular / osc);
    color = mix(color, white, specular);
    color = mix(color, white, .8 - .5 * length(uv));
    color.r += max(0., fbm(fbm2(uv + x_time * .1) + x_time * .05)) * .1;
    color.g += max(0., fbm(fbm2(uv - x_time * .2) + x_time * .1 + 1.)) * .1;
    color.b += max(0., fbm(fbm2(uv + x_time * .3) + x_time * .07 + 2.)) * .1;

    outputColor.rgb = color;
    gl_FragColor = vec4(outputColor.r, outputColor.g, outputColor.b, outputColor.a);
}