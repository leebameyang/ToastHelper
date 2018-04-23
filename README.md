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
	compile 'com.github.leebameyang:ToastHelper:1.0.1'
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
          .setTextSize(Attribute.TYPE_SP, R.dimen.fontSize)
          .setTextColor(R.color.colorPrimary)
	  .text("dd").textColor("WHITE")
          .setForTextViewPadding(<span>R.dimen.paddingTop</span>,
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


# Init Default Attribute
<pre>
     ToastHelper.init(this)
             .setText("Message")
	     .show();
	     
     If you wrote example like this, Applied as Default property.	     
     
     setTextColor(R.color.colorWhite)
     setTextSize(Attribute.TYPE_DIP, context.getResources().getDimension(R.dimen.fontSize))
     setFrame(Attribute.ROUND_FRAME)
     setDuration(Attribute.DURATION_SHORT)
     setGravity(Attribute.GRAVITY_BOTTOM_CENTER)
</pre>

# License
<pre>
Copyright [2018] [LEE MINWOO]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>
