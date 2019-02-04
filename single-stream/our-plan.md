# Our plan

## WA Tribe

----
# Short version

We will be entering the dev stream shortly. 

We will not be exiting.

----
# Long version

Development will be continuing as it did in dev-bc.  Some habits will have to change:

*  Service and Webapp pull requests will need to be release ready.
 
    Specifically, dependencies will have to be released before the pull request can be merged (check to make sure of the exact timing).
  

----
# Long version

* Features will mostly be toggled through provinces.  Developers will be responsible for this.

    - Should there be features that are problematic and difficult to have in the codebase, a feature branch will be created for this feature.
        
        We would have to determine whether an environment is warranted for these developments or the simple sandbox would be appropriate.
----
# Long version

* The development environment used will be Z.

* Contact developers will receive a sandbox environment to integrate their new stuff.  
        
    The life cycle of this environment is yet to be determined, but *a priori*, this environment would not be part of the CICD promotion process.  Preferably, it would be reset periodically to the dev/intg state.
----
# What's our future "get"

Once this is done, it will allow us to bring in further improvements:
  
* Simplification of project structure.

* Standard code format.

* Standard sonar to support standard code practices.

* Simplification of CICD improvements.

* Simplification of release process (even more!)