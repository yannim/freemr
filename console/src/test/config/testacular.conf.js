// Sample Testacular configuration file, that contain pretty much all the available options
// It's used for running client tests on Travis (http://travis-ci.org/#!/vojtajina/testacular)
// Most of the options can be overriden by cli arguments (see testacular --help)
//
// For all available config options and default values, see:
// https://github.com/vojtajina/testacular/blob/stable/lib/config.js#L54


// base path, that will be used to resolve files and exclude
basePath = '../../..';

WEBJARS = 'target/test-js-libs/META-INF/resources/webjars/';
SRC = 'src/main/client/';

// list of files / patterns to load in the browser
files = [
    JASMINE,
    JASMINE_ADAPTER,
    WEBJARS + 'jquery/1.8.3/jquery.js',
    WEBJARS + 'bootstrap/2.2.2/js/bootstrap.js',
    WEBJARS + 'angularjs/1.1.2/angular.js',
    WEBJARS + 'angularjs/1.1.2/angular-mocks.js',
    SRC + 'common/directives/*.js',
    'src/test/common/*.js',
    'src/test/client/*.spec.js'
];

// list of files to exclude
exclude = [
    'adapter/require.src.js'
];

// use dots reporter, as travis terminal does not support escaping sequences
// possible values: 'dots', 'progress', 'junit'
// CLI --reporters progress
reporters = ['progress', 'junit', 'coverage'];

junitReporter = {
    // will be resolved to basePath (in the same way as files/exclude patterns)
    outputFile: 'target/surefire-reports/test-results.xml',
    suite: 'nl.enovation'
};

// web server port
// CLI --port 9876
port = 9876;

// cli runner port
// CLI --runner-port 9100
runnerPort = 9100;

// enable / disable colors in the output (reporters and logs)
// CLI --colors --no-colors
colors = true;

// level of logging
// possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
// CLI --log-level debug
logLevel = LOG_INFO;

// enable / disable watching file and executing tests whenever any file changes
// CLI --auto-watch --no-auto-watch
autoWatch = true;

// Start these browsers, currently available:
// - Chrome
// - ChromeCanary
// - Firefox
// - Opera
// - Safari (only Mac)
// - PhantomJS
// - IE (only Windows)
// CLI --browsers Chrome,Firefox,Safari
browsers = ['Chrome'];

// If browser does not capture in given timeout [ms], kill it
// CLI --capture-timeout 5000
captureTimeout = 5000;

// Auto run tests on start (when browsers are captured) and exit
// CLI --single-run --no-single-run
singleRun = false;

// report which specs are slower than 500ms
// CLI --report-slower-than 500
reportSlowerThan = 500;

// compile coffee scripts
preprocessors = {
//	'**/services/TokenAuthenticationService.js' : 'coverage',
//	'**/**.js' : 'coverage'
};

coverageReporter = {
    type: 'html',
    dir: 'target/coverage/',
    file: 'coverage.txt'
}
