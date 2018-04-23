# ToastHelper

This library can customize toast notifications to your liking,<br/>
and you can also use the image resources you created as a background.

<div>
   <img src="https://user-images.githubusercontent.com/33782600/39126359-13fb1cfc-473d-11e8-9b3d-56a376b1c3a8.png" width="780" height="480">
</div>

# Add to your Project Level build.gradle 
<pre>
  allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
  }
</pre>

# Add to your App Level build.gradle
<pre>
  dependencies {
	compile 'com.github.leebameyang:ToastHelper:1.0.0'
  }
</pre>

# API Level
<pre>
  API 16 ~ 27
</pre>

# Example
<pre>
  ToastHelper.init(context)
          .setText("your message")
          .setTextSize(TypedValue.COMPLEX_UNIT_DIP, R.dimen.fontSize)
          .setTextColor(R.color.colorPrimary)
          .setForTextViewPadding(R.dimen.paddingTop,
			         R.dimen.paddingRight,
		  	         R.dimen.paddingBottom,
				 R.dimen.paddingLeft)
          .setDuration(Attribute.DURATION_SHORT)
          .setAnimation(Attribute.ANIMATION_POP)
          .setFrame(Attribute.ROUND_FRAME) // If you want to use background resources, do not use frames.
	  .setBackground(R.drawable.toast)
	  .setGravity(Attribute.CENTRAL)
          .show();
</pre>
