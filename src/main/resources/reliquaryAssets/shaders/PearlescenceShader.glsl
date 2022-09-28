// online at https://www.shadertoy.com/view/NtcfRf

#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float x_time, alpha;

vec4 rgba() {
    return v_color * texture2D(u_texture, v_texCoords);
}
// from https://github.com/msfeldstein/glsl-map/blob/master/index.glsl
float map(float value, float inMin, float inMax, float outMin, float outMax) {
  return outMin + (outMax - outMin) * (value - inMin) / (inMax - inMin);
}
vec2 map(vec2 value, vec2 inMin, vec2 inMax, vec2 outMin, vec2 outMax) {
  return outMin + (outMax - outMin) * (value - inMin) / (inMax - inMin);
}
// from https://www.shadertoy.com/view/NlSGDz
uint hash(uint x, uint seed) {
    const uint m = 0x5bd1e995U;
    uint hash = seed;
    // process input
    uint k = x;
    k *= m;
    k ^= k >> 24;
    k *= m;
    hash *= m;
    hash ^= k;
    // some final mixing
    hash ^= hash >> 13;
    hash *= m;
    hash ^= hash >> 15;
    return hash;
}
uint hash(uvec2 x, uint seed){
    const uint m = 0x5bd1e995U;
    uint hash = seed;
    // process first vector element
    uint k = x.x; 
    k *= m;
    k ^= k >> 24;
    k *= m;
    hash *= m;
    hash ^= k;
    // process second vector element
    k = x.y; 
    k *= m;
    k ^= k >> 24;
    k *= m;
    hash *= m;
    hash ^= k;
	// some final mixing
    hash ^= hash >> 13;
    hash *= m;
    hash ^= hash >> 15;
    return hash;
}
vec2 gradientDirection(uint hash) {
    switch (int(hash) & 3) { // look at the last two bits to pick a gradient direction
    case 0:
        return vec2(1.0, 1.0);
    case 1:
        return vec2(-1.0, 1.0);
    case 2:
        return vec2(1.0, -1.0);
    case 3:
        return vec2(-1.0, -1.0);
    }
}
float interpolate(float value1, float value2, float value3, float value4, vec2 t) {
    return mix(mix(value1, value2, t.x), mix(value3, value4, t.x), t.y);
}
vec2 fade(vec2 t) {
    // 6t^5 - 15t^4 + 10t^3
	return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
}
float perlinNoise(vec2 position, uint seed) {
    vec2 floorPosition = floor(position);
    vec2 fractPosition = position - floorPosition;
    uvec2 cellCoordinates = uvec2(floorPosition);
    float value1 = dot(gradientDirection(hash(cellCoordinates, seed)), fractPosition);
    float value2 = dot(gradientDirection(hash((cellCoordinates + uvec2(1, 0)), seed)), fractPosition - vec2(1.0, 0.0));
    float value3 = dot(gradientDirection(hash((cellCoordinates + uvec2(0, 1)), seed)), fractPosition - vec2(0.0, 1.0));
    float value4 = dot(gradientDirection(hash((cellCoordinates + uvec2(1, 1)), seed)), fractPosition - vec2(1.0, 1.0));
    return interpolate(value1, value2, value3, value4, fade(fractPosition));
}
vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() {
    vec4 outputColor = rgba();
	vec3 white = vec3(1., 1., 1.);
    vec3 pink = vec3(.75, .666, .6);
    vec3 green = vec3(.74, .75, .71);
	
	// map to the 512/bg_skill_gray cardui atlas region
	vec2 uv = map(v_texCoords, vec2(.45, .255), vec2(.62, .47), vec2(-1, 1), vec2(1, -1));
	
    // bloom
    vec3 color = mix(green, pink, (uv.y + 1.) / 2.);
    uv.y *= 1.66;
    float oscFactor = .05;
    vec2 bloomUVOffset = vec2(sin(x_time * .561), cos(x_time)) * .1;
    float bloom = map(length(uv + bloomUVOffset), 1.66, 2. - oscFactor + sin(x_time * .5) * oscFactor, 1.2, 1.);
    color *= clamp(bloom, 1., 1.45);
    // iridescence
    uint seed = 0x578437adU;
    vec2 perlinUV = (uv + x_time * .1) * 5. + perlinNoise(uv * 2. + x_time * .05, seed - 1U) * 4.;
    float p = perlinNoise(perlinUV - x_time * .02, seed);
    vec3 pColor = hsv2rgb(vec3(p * .5 + .425, .03, 1.));
    color *= pColor;

    outputColor.rgb = color;
	outputColor.a *= alpha;
    gl_FragColor = vec4(outputColor.r, outputColor.g, outputColor.b, outputColor.a);
}